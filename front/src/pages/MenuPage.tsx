import { useQuery } from "@tanstack/react-query";
import { api, type Dish } from "@/lib/api";
import { useCart } from "@/contexts/CartContext";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Plus } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { useAuth } from "@/contexts/AuthContext";

export default function MenuPage() {
  const { data: dishes, isLoading } = useQuery({
    queryKey: ["dishes"],
    queryFn: api.getDishes,
  });
  const { addItem } = useCart();
  const { toast } = useToast();
  const { user } = useAuth();

  const available = dishes?.filter((d) => d.available) ?? [];

  const handleAdd = (dish: Dish) => {
    addItem(dish);
    toast({ title: `${dish.name} ajouté au panier` });
  };

  return (
    <div className="mx-auto max-w-5xl p-4">
      <h1 className="mb-6 text-2xl font-bold">Menu</h1>
      {isLoading && <p className="text-muted-foreground">Chargement des plats...</p>}
      {!isLoading && available.length === 0 && (
        <p className="text-muted-foreground">Aucun plat disponible pour le moment.</p>
      )}
      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
        {available.map((dish) => (
          <Card key={dish.dish_id} className="flex flex-col">
            <CardHeader>
              <CardTitle className="text-lg">{dish.name}</CardTitle>
            </CardHeader>
            <CardContent className="flex-1">
              <p className="text-sm text-muted-foreground">{dish.description}</p>
            </CardContent>
            <CardFooter className="flex items-center justify-between">
              <span className="font-semibold">{dish.price.toFixed(2)} €</span>
              <Button size="sm" onClick={() => handleAdd(dish)}>
                <Plus className="mr-1 h-4 w-4" /> Ajouter au panier
              </Button>
            </CardFooter>
          </Card>
        ))}
      </div>
    </div>
  );
}
