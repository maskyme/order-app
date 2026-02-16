import { Navigate } from "react-router-dom";
import { useAuth } from "@/contexts/AuthContext";

interface Props {
  children: React.ReactNode;
  allowedRole?: "CLIENT" | "COOK";
}

export default function ProtectedRoute({ children, allowedRole }: Props) {
  const { user, isAuthenticated } = useAuth();

  if (!isAuthenticated) return <Navigate to="/login" replace />;
  if (allowedRole && user?.role !== allowedRole) {
    return <Navigate to={user?.role === "COOK" ? "/kitchen" : "/menu"} replace />;
  }

  return <>{children}</>;
}
