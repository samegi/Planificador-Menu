import MacronutrientesCharts from "../../components/MacronutrientesCharts";

export default function StatsMacronutrientes() {
  return (
    <div className="max-w-md mx-auto p-6 space-y-4">
      <h2 className="text-2xl font-semibold mb-4">
        Recetas por macronutriente
      </h2>

      <MacronutrientesCharts />
    </div>
  );
}
