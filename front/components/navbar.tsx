import Link from "next/link";
import { Compass } from "lucide-react";

export function Navbar() {
    return (
        <header className="bg-[#1e3a5f] border-b border-white/10">
            <div className="max-w-7xl mx-auto px-4 h-20 flex items-center justify-between">

                {/* LOGO */}
                <Link href="/" className="flex items-center gap-2 text-white font-bold text-2xl">
                <Compass className="h-8 w-8 text-[#2A9D8F]"/>
                <span>resolve.com</span>
                </Link>
                {/* LINKS (desktop) */}
                <nav className="hidden md:flex items-center gap-8 text-gray-300 text-sm font-medium">
                    <Link href="/servicos" className="hover:text-white transition">Meus Serviços</Link>
                    <Link href="/agenda" className="hover:text-white transition">Agenda</Link>
                    <Link href="/ajuda" className="hover:text-white transition">Central de Ajuda</Link>
                </nav>

                {/* BOTÂO LOGIN */}
                <Link
                  href="/login"
                  className="bg-[#2A9D8F] hover:bg-[#21867a] text-white px-6 py-2 rounded-md font-medium transition"
                  >
                    Login
                  </Link>
            </div>
        </header>
    );
}