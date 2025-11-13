// src/api/http.ts
export const BASE = 'http://localhost:8080/api';

export async function http<T>(url: string, init?: RequestInit): Promise<T> {
  const res = await fetch(url, {
    headers: { 'Content-Type': 'application/json' },
    ...init,
  });
  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(`HTTP ${res.status} ${res.statusText} -> ${text}`);
  }
  return res.json() as Promise<T>;
}
