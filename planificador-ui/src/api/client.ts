const BASE = import.meta.env.VITE_API_URL?.replace(/\/$/, '') || 'http://localhost:8080';

type FetchOpts = RequestInit & { json?: any };
async function req<T>(path: string, opts: FetchOpts = {}): Promise<T> {
  const headers: HeadersInit = { 'Content-Type': 'application/json', ...(opts.headers || {}) };
  const res = await fetch(`${BASE}/api${path}`, {
    ...opts,
    headers,
    body: opts.json !== undefined ? JSON.stringify(opts.json) : opts.body,
  });
  if (!res.ok) {
    const msg = await res.text().catch(()=>'');
    throw new Error(`${res.status} ${res.statusText} - ${msg}`);
  }
  return res.json() as Promise<T>;
}

export default {
  get:  <T>(p: string) => req<T>(p),
  post: <T>(p: string, json?: any) => req<T>(p, { method:'POST', json }),
  put:  <T>(p: string, json?: any) => req<T>(p, { method:'PUT',  json }),
  del:  <T>(p: string) => req<T>(p, { method:'DELETE' }),
  base: BASE,
};
