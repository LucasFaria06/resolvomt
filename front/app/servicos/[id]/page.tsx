"use client";
import { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import { api } from "@/services/api";
import Link from "next/link";
import { ArrowLeft, MapPin, Star, Phone } from "lucide-react";

interface Profissional {
    id: number;
    nome: string;
    profissao: string;
    precoHora: number;
    cidade: string;
}

export default function DetalheProfissional() {
    const params = useParams();
    const [ perfil, setPerfil] = useState<Profissional | null>(null);
    const [ loading, setLoading] = useState(true);

    useEffect(() => {
        if (params.id) {
            api.get(`/profissionais/${params.id}`)
              .then((res) => setPerfil(res.data))
              .catch((err) => alert("Erro ao carregar perfil: " + err.message))
              .finally(() => setLoading(false));
        }  
    }, [params.id]);

    if (loading) return <div className="text-center mt-20">Carregando perfil...</div>
    if (!perfil) return <div className="text-center mt-20">Profissional não encontrado.</div>

    return (
        <div className="min-h-screen bg-gray-50 p-6">
            <div className="max-w-2xl mx-auto bg-white rounded-2xl shadow-lg overflow-hidden">

                {/* Cabeçalho com o botão voltar */}
                <div className="bg-[#1e3a5f] p-6 text-white relative">
                    <Link href="/servicos" className="absolute top-6 left-6 hover:text-gray-300">
                    <ArrowLeft />
                    </Link>
                    <div className="flex flex-col items-center mt-4">
                        <div className="w-24 h-24 bg-white text-[#1e3a5f] rounded-full flex items-center justify-center
                                        text-4xl border-4 border-[#2A9D8F]">
                        👷‍♂️                        
                    </div>
                    <h1 className="text-2xl font-bold mt-4">{perfil.nome}</h1>
                    <p className="text-[#2A9D8F] font-semibold">{perfil.profissao}</p>
                    </div>
                </div>

                {/* Detalhes */}
                <div className="p-8 space-y-6">
                    <div className="flex justify-between items-center border-b pb-4">
                        <span className="text-gray-500 flex items-center gap-2"><MapPin size={18}/>Cidade</span>
                        <span className="font-medium text-gray-800">{perfil.cidade}</span>
                    </div>

                    <div className="flex justify-between items-center border-b pb-4">
                        <span className="text-gray-500 flex items-center gap-2"><Star size={18}/>Avaliação</span>
                        <span className="font-medium text-gray-800">5.0 (Iniciante)</span>
                    </div>

                    <div className="flex justify-between items-center border-b pb-4">
                        <span className="text-gray-500">Valor Hora</span>
                        <span className="text-2xl font-bold text-[#2A9D8F]">R$ {perfil.precoHora}</span>
                    </div>

                    {/* Botão de contato */}
                    <button className="w-full bg-[#25D366] hover:bg-[#1da851] text-white py-4 rounded-xl font-bold text-lg
                                       flex items-center justify-center gap-3 transition shadow-md">
                       <Phone />
                       Chamar no WhatsApp                 
                    </button>
                </div>

            </div>
        </div>
    
);
}