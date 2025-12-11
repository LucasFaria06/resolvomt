"use client";
import { MapPin, Search } from "lucide-react";
import { useState, useEffect } from "react";
import { api } from "@/services/api";
import { div, p } from "framer-motion/client";
import Link from "next/link";

interface Profissional {
    id: number;
    nome: string;
    profissao: string;
    precoHora: number;
    cidade: string;
}

export default function ServicosPage() {
    const [profissionais, setProfissionais] = useState<Profissional[]>([]);
    const [loading, setLoading] = useState(true);
    const [busca , setBusca] = useState("");

    async function carregarDados() {
        try {
            const resposta = await api.get("/profissionais");
            setProfissionais(resposta.data);
        } catch (erro) {
            console.error("Erro ao buscar do java:", erro);
            alert("Erro ao conectar com o servidor. O Java está rodando?");
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        carregarDados();
    }, []);

    const profissionaisFiltrados = profissionais.filter((prof) => {
        const nome = (prof.nome || "").toLowerCase();
        const profissao = (prof.profissao || "").toLowerCase();
        const termoBusca = (busca || "").toLowerCase();

        return nome.includes(termoBusca) || profissao.includes(termoBusca)
    })

    return (
        <div className="min-h-screen bg-gray-50 pt-8 pb-12 px-4">
            <div className="max-w-4xl mx-auto">

                {/* CABEÇALHO */}
                <div className="flex flex-col md:flex-row justify-between items-center mb-8 gap-4">
                    <h1 className="text-2xl font-bold text-gray-800">
                        Profissionais Disponíveis
                    </h1>

                    <div className="relative w-full md:w-96">
                        <Search className="absolute left-3 top-3 text-gray-400 h-5 w-5"/>
                        <input 
                        type="text"
                        placeholder="Buscar por nome ou profissãao..." 
                        className="w-full pl-10 pr-4 py-2 border rounded-lg ourline-none focus:border-[#2A9D8F]"
                        value={busca}
                        onChange={(e) => setBusca(e.target.value)}
                        />
                    </div>
                </div>

                {/* LOADING (aparece enquanto o Java não responde) */}
                {loading && <p className="text-center text-gray-500">Carregando dados do sistema...</p>}

                {/* LISTA DE CARDS */}
                <div className="grid gap-4">
                    {profissionaisFiltrados.map((prof) => (
                    <div key={prof.id} className="bg-white p-6 rounded-xl shadow-sm hover:shadow-md transition border 
                                                      border-gray-100 flex flex-col md:flex-row gap-6 items-center">

                        {/* foto (usando uma genérica por enquanto) */}
                        <div className="w-20 h-20 bg-gray-200 rounded-full flex items-center justify-center text-2xl">
                            👷‍♂️
                        </div> 

                        <div className="flex-1 text-center md:text-left">
                            <h2 className="text-xl font-bold text-[#1e3a5f]">{prof.nome}</h2>
                            <p className="text-[#2A9D8F] font-medium">{prof.profissao}</p>
                        </div>

                        <div className="flex items-center justify-center md:justify-start gap-4 mt-2 text-sm text-gray-500">
                            <span className="flex items-center gap-1"><MapPin size={14}/> {prof.cidade}</span>
                            <span className="font-bold text-gray-700">R$ {prof.precoHora}/hora</span>
                        </div>
                        <Link href={`/servicos/${prof.id}`}>
                        <button className="bg-[#2A9D8F] text-white px-6 py-2 rounded-lg font-bold hover:bg-[#21867a] transition">
                            Ver Perfil
                        </button>
                        </Link>
                    </div>
                    ))}
                    {/* Se não achar nada */}
                    {!loading && profissionaisFiltrados.length === 0 && (
                        <p className="text-center text-gray-400 mt-10">Nenhum profissional encontrado.</p>
                    )}
                </div>
            </div>
        </div>
    );
}