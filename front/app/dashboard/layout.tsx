import { Sidebar } from "@/components/Sidebar";
import { div } from "framer-motion/client";

export default function DashboardLayout({
    children,
}: {
    children: React.ReactNode;
}) {
    return (
        <div className="flex min-h-screen bg-gray-50">
            
            {/* Menu lateral fixo */}
            <Sidebar />

            <main className="flex-1 ml-64 p-8">
                { children }
            </main>
        </div>
    );
}
