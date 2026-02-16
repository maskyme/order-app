import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { api, type Order } from "@/lib/api";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useToast } from "@/hooks/use-toast";

const statusColor: Record<string, string> = {
  CREATED: "bg-muted text-muted-foreground",
  PENDING: "bg-yellow-100 text-yellow-800",
  PREPARING: "bg-blue-100 text-blue-800",
  READY: "bg-green-100 text-green-800",
  DELIVERED: "bg-secondary text-secondary-foreground",
};

export default function KitchenPage() {
  const queryClient = useQueryClient();
  const { toast } = useToast();

  const { data: orders, isLoading } = useQuery({
    queryKey: ["all-orders"],
    queryFn: api.getOrders,
    refetchInterval: 5000,
  });

  const mutation = useMutation({
    mutationFn: ({ id, status }: { id: number; status: string }) =>
      api.updateOrderStatus(id, status),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["all-orders"] });
      toast({ title: "Statut mis à jour" });
    },
    onError: (err) => {
      toast({ title: "Erreur lors de la mise à jour", description: String(err), variant: "destructive" });
    },
  });

  const filterOrders = (status?: string) => {
    if (!orders) return [];
    const filtered = status ? orders.filter((o) => o.status === status) : orders;
    return [...filtered].sort(
      (a, b) => new Date(a.createdAt ?? 0).getTime() - new Date(b.createdAt ?? 0).getTime()
    );
  };

  const renderOrder = (order: Order) => (
    <Card key={order.id}>
      <CardHeader className="flex flex-row items-center justify-between pb-2">
        <CardTitle className="text-base">Commande #{order.id}</CardTitle>
        <Badge className={statusColor[order.status] ?? ""}>{order.status}</Badge>
      </CardHeader>
      <CardContent>
        {order.items && order.items.length > 0 && (
          <div className="mb-3 text-sm">
            {order.items.map((item) => (
              <p key={item.id}>
                {item.name ?? `Plat #${item.id}`} × {item.quantity}
              </p>
            ))}
          </div>
        )}
        <div className="flex gap-2 flex-wrap">
          {(order.status === "CREATED" || order.status === "PENDING") && (
            <Button
              size="sm"
              onClick={() => mutation.mutate({ id: order.id, status: "PREPARING" })}
              disabled={mutation.isPending}
            >
              Commencer la préparation
            </Button>
          )}
          {order.status === "PREPARING" && (
            <Button
              size="sm"
              onClick={() => mutation.mutate({ id: order.id, status: "READY" })}
              disabled={mutation.isPending}
            >
              Marquer comme prêt
            </Button>
          )}
          {order.status === "READY" && (
            <Button
              size="sm"
              variant="secondary"
              onClick={() => mutation.mutate({ id: order.id, status: "DELIVERED" })}
              disabled={mutation.isPending}
            >
              Marquer comme livré
            </Button>
          )}
        </div>
      </CardContent>
    </Card>
  );

  return (
    <div className="mx-auto max-w-4xl p-4">
      <h1 className="mb-6 text-2xl font-bold">Tableau de bord de la cuisine</h1>
      {isLoading && <p className="text-muted-foreground">Chargement des commandes...</p>}
      <Tabs defaultValue="all">
        <TabsList>
          <TabsTrigger value="all">Toutes</TabsTrigger>
          <TabsTrigger value="CREATED">Créée</TabsTrigger>
            <TabsTrigger value="PENDING">En attente</TabsTrigger>
          <TabsTrigger value="PREPARING">En préparation</TabsTrigger>
          <TabsTrigger value="READY">Prêt</TabsTrigger>
        </TabsList>
        {["all", "CREATED", "PENDING", "PREPARING", "READY"].map((tab) => (
          <TabsContent key={tab} value={tab} className="space-y-3 mt-4">
            {filterOrders(tab === "all" ? undefined : tab).length === 0 ? (
              <p className="text-muted-foreground text-sm">Aucune commande.</p>
            ) : (
              filterOrders(tab === "all" ? undefined : tab).map(renderOrder)
            )}
          </TabsContent>
        ))}
      </Tabs>
    </div>
  );
}
