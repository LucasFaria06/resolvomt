"use client";

import { Search } from "lucide-react";
import { useState} from "react";

export default function Home() {
  const [busca, setBusca] = useState("");

  function pesquisar() {
    if (!busca) return alert("Digite algo para buscar!");
    alert("Buscando por:" + busca);

  }
  return (
    <main className="min-h-screen bg-gray-50">
      {/*HERO SECTION */}
      <section className="bg-[#1e3a5f] text-white py-20 px-4">
      <div className="max-w=4xl mx-auto text-center space-y-6">
        <h1 className="text-4xl md:text-5xl front-bold">
          Encontre o serviço ideal <br />para o seu dia a dia.
        </h1>
        <p className="text-gray-300 text-lg">
          De eletricistas a advogados, o ResolvoMT conecta você.
        </p>

        {/* BARRA DE BUSCA*/}
        <div className="bg-white p-2 rounded-full flex items-center max-w-2xl mx-auto shadow-2xl border-white/10 backdrop-blur-sm">
          <Search className="text-gray-400 ml-4 h-6 w-6" />
          <input 
          type="text"
          placeholder="O que você precisa hoje? (Ex: Eletricista, Diarista...)"
          className="flex-1 p-3 outline-none text-gray-800" 
          value={busca}
          onChange={(e) => setBusca(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && pesquisar()}
          />
          <button 
          onClick={pesquisar}
          className="bg-[#2A9D8F] hover:bg-[#21867a] text-white px-8 py-4 rounded-full font-bold transition shadow-lg"
          >
            Buscar
          </button>
        </div>
      </div>
      </section>

      {/* SERVIÇOS POPULARES */}
      <section className="max-w-6xl mx-auto py-16 px-4">
        <h2 className="text-2xl font-bold mb-8 text-gray-800 ">Serviços Populares</h2>

        {/* GRID DE CARDS */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-6">

          {/* Card 1 */}
          <div className="bg-white p-6 rounded-xl shadow-sm hover:shadow-xl hover:-translate-y-2 translation-all duration-300 
                          cursor-pointer border border-gray-100">
            <div className="h-14 w-14 bg blue-50 rounded-full mb-4 flex items-center justify-center text-blue-600 text-2xl 
                          group-hover:scale-110 transition-transform duration-300">
              🧹
            </div>
            <h3 className="font-bold text-lg text-gray-900">Diarista</h3>
            <p className="text-sm text-gray-500">Diaristas e Faxineiros</p>
          </div>

          {/* Card 2 */}
          <div className="bg-white p-6 rounded-xl shadow-sm hover:shadow-xl hover:-translate-y-2 translation-all duration-300 
                          cursor-pointer border border-gray-100">
            <div className="h-14 w-14  bg-green-50 rounded-full mb-4 flex items-center justify-center text-green-600 text-2xl 
                          group-hover:scale-110 transition-transform duration-300">
              🔧
            </div>
            <h3 className="font-bold text-lg text-gray-900">Manutenção</h3>
            <p className="text-sm text-gray-500">Eletricistas e Encanadores</p>
          </div>

          {/* Card 3 */}
          <div className="bg-white p-6 rounded-xl shadow-sm hover:shadow-md hover:-translate-y-2 translation-all duration-300 
                          cursor-pointer border border-gray-100">
          <div className="h-14 w-14  bg-purple-50 rounded-full mb-4 flex items-center justify-center text-purple-600 text-2xl 
                          group-hover:scale-110 transition-transform duration-300">
            ⚖️
          </div>
          <h3 className="font-bold text-lg text-gray-900">Jurídico</h3>
          <p className="text-sm text-gray-500">Advogados e Consultores</p>
        </div>

        {/* Card 4 */}
        <div className="bg-white p-6 rounded-xl shadow-sm hover:shadow-xl hover:-translate-y-2 translation-all duration-300 
                        cursor-pointer border border-gray-100">
         <div className="h-14 w-14  bg-orange-50 rounded-full mb-4 flex items-center justify-center text-orange-600 text-2xl 
                          group-hover:scale-110 transition-transform duration-300">
            🚜
          </div>
          <h3 className="font-bold text-lg text-gray-900">Agro</h3>
          <p className="text-sm text-gray-500">Serviços Rurais</p>
          </div>
        </div>
      </section>
    </main>
  );
}
