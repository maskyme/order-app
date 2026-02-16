import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { api } from "@/lib/api";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/hooks/use-toast";

export default function RegisterPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState<"CLIENT" | "COOK">("CLIENT");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const { toast } = useToast();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      await api.register(username, password, role);
      toast({ title: "Compte créé!", description: "Vous pouvez maintenant vous connecter." });
      navigate("/login");
    } catch (err) {
      toast({ title: "Erreur lors de l'inscription", description: String(err), variant: "destructive" });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-background p-4">
      <Card className="w-full max-w-md">
        <CardHeader className="text-center">
          <CardTitle className="text-2xl">🍽️ Créer un compte</CardTitle>
          <CardDescription>Rejoignez RestauOrder</CardDescription>
        </CardHeader>
        <form onSubmit={handleSubmit}>
          <CardContent className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="username">Nom d'utilisateur</Label>
              <Input
                id="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="password">Mot de passe</Label>
              <Input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <div className="space-y-2">
              <Label>Rôle</Label>
              <div className="flex gap-3">
                <Button
                  type="button"
                  variant={role === "CLIENT" ? "default" : "outline"}
                  className="flex-1"
                  onClick={() => setRole("CLIENT")}
                >
                  🧑 Client
                </Button>
                <Button
                  type="button"
                  variant={role === "COOK" ? "default" : "outline"}
                  className="flex-1"
                  onClick={() => setRole("COOK")}
                >
                  👨‍🍳 Cuisinier
                </Button>
              </div>
            </div>
          </CardContent>
          <CardFooter className="flex flex-col gap-3">
            <Button type="submit" className="w-full" disabled={loading}>
              {loading ? "Création en cours..." : "Créer un compte"}
            </Button>
            <p className="text-sm text-muted-foreground">
              Vous avez déjà un compte?{" "}
              <Link to="/login" className="text-primary underline">
                Se connecter
              </Link>
            </p>
          </CardFooter>
        </form>
      </Card>
    </div>
  );
}
