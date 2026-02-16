import { useQuery } from "@tanstack/react-query";
import { api } from "@/lib/api";
import { useAuth } from "@/contexts/AuthContext";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

const STATUS_ORDER = [
  "CREATED",
  "PENDING",
  "PREPARING",
  "READY",
  "DELIVERED",
] as const;

const statusColor: Record<string, string> = {
  CREATED: "bg-muted text-muted-foreground",
  PENDING: "bg-yellow-100 text-yellow-800",
  PREPARING: "bg-blue-100 text-blue-800",
  READY: "bg-green-100 text-green-800",
  DELIVERED: "bg-secondary text-secondary-foreground",
};

export default function OrdersPage() {
  const { user } = useAuth();

  const { data: orders, isLoading } = useQuery({
    queryKey: ["user-orders", user?.userId],
    queryFn: () => api.getUserOrders(user!.userId),
    enabled: !!user,
    refetchInterval: 5000,
  });

  return (
    <div className="mx-auto max-w-3xl p-4">
      <h1 className="mb-6 text-2xl font-bold">Mes commandes</h1>

      {isLoading && (
        <p className="text-muted-foreground">Chargement des commandes...</p>
      )}

      {!isLoading && (!orders || orders.length === 0) && (
        <p className="text-muted-foreground">
          Vous n'avez pas encore passé de commande.
        </p>
      )}

      <div className="space-y-4">
        {orders?.map((order) => {
          const currentIndex = STATUS_ORDER.indexOf(order.status);
          const progressPercent =
            currentIndex <= 0
              ? 0
              : (currentIndex / (STATUS_ORDER.length - 1)) * 100;

          return (
            <Card key={order.id}>
              <CardHeader className="flex flex-row items-center justify-between pb-2">
                <CardTitle className="text-base">
                  Commande #{order.id}
                </CardTitle>

                <Badge className={statusColor[order.status] ?? ""}>
                  {order.status}
                </Badge>
              </CardHeader>

              <CardContent>
                {/* ===== Progress Stepper ===== */}
                <div className="relative mb-5">
                  {/* full track */}
                  <div className="absolute top-2 left-0 right-0 h-0.5 bg-muted" />

                  {/* progress fill */}
                  <div
                    className="absolute top-2 left-0 h-0.5 bg-primary transition-all"
                    style={{ width: `${progressPercent}%` }}
                  />

                  {/* steps */}
                  <div
                    className="grid"
                    style={{
                      gridTemplateColumns: `repeat(${STATUS_ORDER.length}, 1fr)`,
                    }}
                  >
                    {STATUS_ORDER.map((status, i) => (
                      <div
                        key={status}
                        className="flex flex-col items-center relative"
                      >
                        <div
                          className={`h-3 w-3 rounded-full z-10 ${
                            i <= currentIndex
                              ? "bg-primary"
                              : "bg-muted"
                          }`}
                        />

                        <span className="mt-2 text-[10px] text-muted-foreground text-center">
                          {status.slice(0, 4)}
                        </span>
                      </div>
                    ))}
                  </div>
                </div>

                {/* ===== Items ===== */}
                {order.items && order.items.length > 0 && (
                  <div className="mt-3 text-sm text-muted-foreground">
                    {order.items.map((item) => (
                      <p key={item.id}>
                        {item.name ?? `Plat #${item.id}`} × {item.quantity}
                      </p>
                    ))}
                  </div>
                )}
              </CardContent>
            </Card>
          );
        })}
      </div>
    </div>
  );
}
