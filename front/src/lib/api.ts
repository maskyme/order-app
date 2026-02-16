const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

function getToken(): string | null {
  return localStorage.getItem("jwt_token");
}

export function setToken(token: string) {
  localStorage.setItem("jwt_token", token);
}

export function clearToken() {
  localStorage.removeItem("jwt_token");
}

export function decodeJwt(token: string): { sub: string; role: string; userId: number; [key: string]: unknown } {
  const payload = token.split(".")[1];
  const decoded = JSON.parse(atob(payload));
  return decoded;
}

async function request<T>(
  endpoint: string,
  options: RequestInit = {}
): Promise<T> {
  const token = getToken();
  const headers: Record<string, string> = {
    "Content-Type": "application/json",
    ...(options.headers as Record<string, string>),
  };
  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  const res = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers,
  });

  if (!res.ok) {
    const errorText = await res.text().catch(() => "Request failed");
    throw new Error(errorText || `HTTP ${res.status}`);
  }

  const text = await res.text();
  if (!text) return undefined as T;
  return JSON.parse(text) as T;
}

export const api = {
  login: (username: string, password: string) =>
    request<{ token: string }>("/api/auth/login", {
      method: "POST",
      body: JSON.stringify({ username, password }),
    }),

  register: (username: string, password: string, role: string) =>
    request<unknown>("/api/users/register", {
      method: "POST",
      body: JSON.stringify({ username, password, role }),
    }),

  getDishes: () => request<Dish[]>("/api/dishes"),
  getDish: (id: number) => request<Dish>(`/api/dishes/${id}`),

  createOrder: (clientId: number) =>
    request<Order>("/api/orders", {
      method: "POST",
      body: JSON.stringify({ client_id: clientId }),
    }),

  addOrderItem: (orderId: number, dishId: number, quantity: number) =>
    
  request<unknown>("/api/order-items", {
    method: "POST",
    body: JSON.stringify({ orderId, dishId, quantity }),
  }),


  getOrders: () => request<Order[]>("/api/orders"),
  getOrder: (id: number) => request<Order>(`/api/orders/${id}`),
  getUserOrders: (userId: number) => request<Order[]>(`/api/users/${userId}/orders`),

  updateOrderStatus: (orderId: number, status: string) =>
    request<Order>(`/api/orders/${orderId}/status`, {
      method: "PATCH",
      body: JSON.stringify(status),
      headers: { "Content-Type": "application/json" },
    }),
};

export interface Dish {
  dish_id: number;
  name: string;
  description: string;
  price: number;
  available: boolean;
}

export interface OrderItem {
  id: number;
  dishId: number;
  name?: string;
  quantity: number;
}

export interface Order {
  id: number;
  clientId: number;
  name: string;
  status: "CREATED" | "PENDING" | "PREPARING" | "READY" | "DELIVERED";
  createdAt?: string;
  items?: OrderItem[];
}
