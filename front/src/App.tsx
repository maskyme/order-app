import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider, useAuth } from "@/contexts/AuthContext";
import { CartProvider } from "@/contexts/CartContext";
import ProtectedRoute from "@/components/ProtectedRoute";
import Navbar from "@/components/Navbar";
import LoginPage from "@/pages/LoginPage";
import RegisterPage from "@/pages/RegisterPage";
import MenuPage from "@/pages/MenuPage";
import CartPage from "@/pages/CartPage";
import OrdersPage from "@/pages/OrdersPage";
import KitchenPage from "@/pages/KitchenPage";
import NotFound from "@/pages/NotFound";

const queryClient = new QueryClient();

function AppRoutes() {
  const { isAuthenticated, user } = useAuth();
  return (
    <>
      {isAuthenticated && <Navbar />}
      <main className="min-h-[calc(100vh-3.5rem)]">
        <Routes>
          <Route
            path="/"
            element={
              isAuthenticated ? (
                <Navigate to={user?.role === "COOK" ? "/kitchen" : "/menu"} replace />
              ) : (
                <Navigate to="/login" replace />
              )
            }
          />
          <Route path="/login" element={isAuthenticated ? <Navigate to="/" replace /> : <LoginPage />} />
          <Route path="/register" element={isAuthenticated ? <Navigate to="/" replace /> : <RegisterPage />} />
          <Route path="/menu" element={<ProtectedRoute allowedRole="CLIENT"><MenuPage /></ProtectedRoute>} />
          <Route path="/cart" element={<ProtectedRoute allowedRole="CLIENT"><CartPage /></ProtectedRoute>} />
          <Route path="/orders" element={<ProtectedRoute allowedRole="CLIENT"><OrdersPage /></ProtectedRoute>} />
          <Route path="/kitchen" element={<ProtectedRoute allowedRole="COOK"><KitchenPage /></ProtectedRoute>} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </main>
    </>
  );
}

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <BrowserRouter>
        <AuthProvider>
          <CartProvider>
            <AppRoutes />
          </CartProvider>
        </AuthProvider>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
