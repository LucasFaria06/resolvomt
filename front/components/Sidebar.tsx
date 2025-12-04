import Link from "next/link";
import { LayoutDashboard, Calendar, Settings, LogOut, User } from "lucide-react";

export function Sidebar() {
    return (
        <aside className="w-64 bg-white h-screen border-r border-gray-200 flex flex-col fixed left-0 top-0">

            { /* 1. LOGO DA ÁREA LOGADA */}
            <div className="h-20 flex items-center justify-center border-b border-gray-100">
                <h1 className="text-xl font-bold text-[#1e3a5f]">Resolvo<span className="text-[#2A9D8F]">MT</span></h1>
            </div>

            {/* 2. MENU DE NAVEGAÇÃO */}
            <nav className="flex-1 px-4 py-6 space-y-2">

                <p className="text-xs font-semibold text-gray-400 uppercase mb-4 px-2">Principal</p>

                <Link href="/dashboard" className="flex items-center gap-3 px-4 py-3 text-gray-700 hover:bg-blue hover:text-[#2A9D8F]
                                                   rounded-lg transition font-medium">
                <LayoutDashboard size={20}/>
                <span>Visão Geral</span>
                </Link>

                <Link href="/dashboard" className="flex items-center gap-3 px-4 py-3 text-gray-700 hover:bg-blue hover:text-[#2A9D8F]
                                                   rounded-lg transition font-medium">
                <LayoutDashboard size={20}/>
                <span>Meus Agendamentos</span>
                </Link>

                <Link href="/dashboard" className="flex items-center gap-3 px-4 py-3 text-gray-700 hover:bg-blue hover:text-[#2A9D8F]
                                                   rounded-lg transition font-medium">
                <LayoutDashboard size={20}/>
                <span>Meu perfil</span>
                </Link>

                <Link href="/dashboard" className="flex items-center gap-3 px-4 py-3 text-gray-700 hover:bg-blue hover:text-[#2A9D8F]
                                                   rounded-lg transition font-medium">
                <LayoutDashboard size={20}/>
                <span>Configurações</span>
                </Link>

            </nav>

            {/* 3. RODAPÉ DO MENU (LOGOUT) */}
            <div className="p-4 border-t border-gray-100">
                <button className="flex items-center gap-3 w-full px-4 py-3 text-red-600 hover:bg-red-50 rounded-lg transition font-medium">
                    <LogOut size={20}/>
                    <span>Sair do Sistema</span>
                </button>
            </div>
        </aside>
    );
}