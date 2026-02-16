import React, { createContext, useContext, useState, useEffect, useCallback } from "react";
import { setToken, clearToken, decodeJwt } from "@/lib/api";

interface AuthUser {
  username: string;
  role: "CLIENT" | "COOK";
  userId: number;
}

interface AuthContextType {
  user: AuthUser | null;
  login: (token: string) => void;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<AuthUser | null>(null);

  const login = useCallback((token: string) => {
    setToken(token);
    const decoded = decodeJwt(token);
    setUser({
      username: decoded.sub,
      role: decoded.role as "CLIENT" | "COOK",
      userId: decoded.userId,
    });
  }, []);

  const logout = useCallback(() => {
    clearToken();
    setUser(null);
  }, []);

  useEffect(() => {
    const token = localStorage.getItem("jwt_token");
    if (token) {
      try {
        const decoded = decodeJwt(token);
        setUser({
          username: decoded.sub,
          role: decoded.role as "CLIENT" | "COOK",
          userId: decoded.userId,
        });
      } catch {
        clearToken();
      }
    }
  }, []);

  return (
    <AuthContext.Provider value={{ user, login, logout, isAuthenticated: !!user }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth must be used within AuthProvider");
  return ctx;
}
