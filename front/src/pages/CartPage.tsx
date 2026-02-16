import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useCart } from "@/contexts/CartContext";
import { useAuth } from "@/contexts/AuthContext";
import { api } from "@/lib/api";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Minus, Plus, Trash2, ShoppingBag } from "lucide-react";
import { useToast } from "@/hooks/use-toast";

export default function CartPage() {
  const { items, updateQuantity, removeItem, clearCart, total } = useCart();
  const { user } = useAuth();
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const { toast } = useToast();

  const placeOrder = async () => {
    if (!user || items.length === 0) return;
    setLoading(true);
    try {
      const order = await api.createOrder(user.userId);
      await Promise.all(
        items.map((item) =>
          api.addOrderItem(order.id, item.dish.dish_id, item.quantity)
        )
      );
      
      clearCart();
      toast({ title: "Commande confirmée!", description: `Commande #${order.id} a été créée.` });
      navigate("/orders");
    } catch (err) {
      toast({ title: "Erreur lors de la commande", description: String(err), variant: "destructive" });
    } finally {
      setLoading(false);
    }
  };

  if (items.length === 0) {
    return (
      <div className="mx-auto flex max-w-5xl flex-col items-center justify-center p-8 text-center">
        <ShoppingBag className="mb-4 h-16 w-16 text-muted-foreground/40" />
        <h2 className="mb-2 text-xl font-semibold">Votre panier est vide</h2>
        <p className="text-muted-foreground">Allez au menu pour ajouter des plats!</p>
        <Button className="mt-4" onClick={() => navigate("/menu")}>
          Voir le menu
        </Button>
      </div>
    );
  }

  return (
    <div className="mx-auto max-w-2xl p-4">
      <h1 className="mb-6 text-2xl font-bold">Votre panier</h1>
      <div className="space-y-3">
        {items.map((item) => (
          <Card key={item.dish.dish_id}>
            <CardContent className="flex items-center justify-between p-4">
              <div className="flex-1">
                <p className="font-medium">{item.dish.name}</p>
                <p className="text-sm text-muted-foreground">
                  {item.dish.price.toFixed(2)} € × {item.quantity} ={" "}
                  {(item.dish.price * item.quantity).toFixed(2)} €
                </p>
              </div>
              <div className="flex items-center gap-2">
                <Button
                  variant="outline"
                  size="icon"
                  className="h-8 w-8"
                  onClick={() => updateQuantity(item.dish.dish_id, item.quantity - 1)}
                >
                  <Minus className="h-3 w-3" />
                </Button>
                <span className="w-6 text-center text-sm">{item.quantity}</span>
                <Button
                  variant="outline"
                  size="icon"
                  className="h-8 w-8"
                  onClick={() => updateQuantity(item.dish.dish_id, item.quantity + 1)}
                >
                  <Plus className="h-3 w-3" />
                </Button>
                <Button
                  variant="ghost"
                  size="icon"
                  className="h-8 w-8 text-destructive"
                  onClick={() => removeItem(item.dish.dish_id)}
                >
                  <Trash2 className="h-3 w-3" />
                </Button>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>

      <Card className="mt-6">
        <CardHeader>
          <CardTitle className="text-lg">Récapitulatif de la commande</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex justify-between text-lg font-semibold">
            <span>Total :</span>
            <span>{total.toFixed(2)} €</span>
          </div>
        </CardContent>
        <CardFooter>
          <Button className="w-full" size="lg" onClick={placeOrder} disabled={loading}>
            {loading ? "En cours de commande..." : "Passer la commande"}
          </Button>
        </CardFooter>
      </Card>
    </div>
  );
}
