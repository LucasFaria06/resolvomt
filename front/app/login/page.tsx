"use client";
import {use, useState } from "react";
import Link from "next/link";

export default function LoginPage() {
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");

    function entrar(e: any)  {
        e.prevenDefault();
        alert("Tentando logar com:" + email);
    }

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
            <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-md">
                <h1 className="text-2xl font-bold text-center text-[#1e3a5f] mb-6">
                    Acesse sua conta
                </h1>

                <form onSubmit={entrar} className="space-y-4">

                    <div>
                        <label className="block text-sm font-medium text-gray-700">E-mail</label>
                        <input 
                        type="email"
                        className="w-full p-3 border rounded-lg outline-none focus:border-[2A9D8F]"
                        placeholder="seu@email.com"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-gray-700">Senha</label>
                        <input 
                        type="password"
                        className="w-full p-3 border rounded-lg outline-none focus:border-[#2A9D8F]"
                        placeholder="********"
                        value={senha}
                        onChange={(e) => setSenha(e.target.value)}
                        />
                    </div>

                    <button
                    type="submit"
                    className="w-full bg-[#2A9D8F] hover:bg-[#21867a] text-white py-3 rounded-lg font-bold transition"
                    >
                        Entrar
                    </button>
                </form>

                <p className="text-center mt-6 text-sm text-gray-500">
                    Ainda não tem conta?{" "}
                    <Link href="/cadastro" className="text-[#2A9D8F] font-bold hover:underline">
                    Crie agora</Link>
                </p>
            </div>
        </div>
    );
}