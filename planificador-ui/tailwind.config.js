/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html","./src/**/*.{ts,tsx}"],
  theme: {
    extend: {
      colors: {
        brand: {
          50:  "#f7f6ff",
          100: "#efeaff",
          200: "#ded5ff",
          300: "#c0aaff",
          400: "#a07dff",
          500: "#8b5cf6",   // acento morado
          600: "#7c3aed",
          700: "#6d28d9",
          800: "#5b21b6",
          900: "#4c1d95",
        }
      }
    },
  },
  plugins: [],
}
