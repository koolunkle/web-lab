import { Outlet, useNavigation } from "react-router-dom";
import Footer from "./components/footer/Footer";
import Header from "./components/Header";

function App() {
  const navigation = useNavigation();
  return (
    <>
      <Header />
      {navigation.state === "loading" ? (
        <div className="flex items-center justify-center min-h-[852px]">
          <span className="text-2xl font-semibold text-primary dark:text-light">
            Loading...
          </span>
        </div>
      ) : (
        <Outlet />
      )}
      <Footer />
    </>
  );
}

export default App;
