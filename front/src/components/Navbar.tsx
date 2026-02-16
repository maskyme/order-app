import { useAuth } from "@/contexts/AuthContext";
import { useCart } from "@/contexts/CartContext";
import { Button } from "@/components/ui/button";
import { Link, useLocation } from "react-router-dom";
import { LogOut, ShoppingCart, UtensilsCrossed } from "lucide-react";

export default function Navbar() {
  const { user, logout } = useAuth();
  const location = useLocation();

  return (
    <header className="sticky top-0 z-50 border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      <div className="mx-auto flex h-14 max-w-5xl items-center justify-between px-4">
        <div className="flex items-center gap-6">
          <Link to="/" className="flex items-center gap-2 font-semibold text-lg">
            <UtensilsCrossed className="h-5 w-5" />
            RestauOrder
          </Link>

          {user?.role === "CLIENT" && (
            <nav className="flex gap-4 text-sm">
              <Link
                to="/menu"
                className={`transition-colors hover:text-foreground ${location.pathname === "/menu" ? "text-foreground font-medium" : "text-muted-foreground"}`}
              >
                Menu
              </Link>
              <Link
                to="/orders"
                className={`transition-colors hover:text-foreground ${location.pathname === "/orders" ? "text-foreground font-medium" : "text-muted-foreground"}`}
              >
                Mes commandes
              </Link>
            </nav>
          )}

          {user?.role === "COOK" && (
            <nav className="flex gap-4 text-sm">
              <Link
                to="/kitchen"
                className={`transition-colors hover:text-foreground ${location.pathname === "/kitchen" ? "text-foreground font-medium" : "text-muted-foreground"}`}
              >
                Tableau de bord
              </Link>
            </nav>
          )}
        </div>

        <div className="flex items-center gap-3">
          {user?.role === "CLIENT" && <CartButton />}
          <span className="text-sm text-muted-foreground">{user?.username}</span>
          <Button variant="ghost" size="icon" onClick={logout}>
            <LogOut className="h-4 w-4" />
          </Button>
        </div>
      </div>
    </header>
  );
}

function CartButton() {
  const { itemCount } = useCart();
  return (
    <Link to="/cart">
      <Button variant="ghost" size="icon" className="relative">
        <ShoppingCart className="h-4 w-4" />
        {itemCount > 0 && (
          <span className="absolute -top-1 -right-1 flex h-5 w-5 items-center justify-center rounded-full bg-primary text-[10px] text-primary-foreground">
            {itemCount}
          </span>
        )}
      </Button>
    </Link>
  );
}
