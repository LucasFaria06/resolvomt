export default function DashboardPage() {
    return (
        <div>
            <h1 className="text-2xl font-bold text-gray-800 mb-6">Olá, Lucas!</h1>

            {/* GRID DE RESUMO */}
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">

            {/* Card 1 */}
            <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
                <p className="text-gray-500 text-sm">Serviços este mês</p>
                <h2 className="text-3xl font-bold text-[#1e3a5f]">12</h2>
            </div>
            {/* Card 1 */}
            <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
                <p className="text-gray-500 text-sm">Total Gasto</p>
                <h2 className="text-3xl font-bold text-[#2A9D8F]">R$ 850,00</h2>
            </div>
            {/* Card 3 */}
            <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100">
               <p className="text-gray-500 text-sm">Próximo Agendamento</p>
               <h2 className="text-lg font-bold text-gray-800 mt-1">Amanhâ, 14:00</h2>
               <span className="text-ts text-blue-600 bg-blue-50 px-2 py-1 rounded-full">Ar Condicionado</span>
            </div>
            
            </div>
        </div>
    );
}