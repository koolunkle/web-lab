import "./App.css";

function App() {
  return (
    <>
      <h1 class="text-3xl font-bold underline">Hello world!</h1>
      <p className="text-lg underline font-bold mt-4 text-blue-600">
        Tailwind makes styling easy!
      </p>
      <div className="bg-gray-200 p-4 m-4">Background color demo</div>
      <div className="bg-green-300 border border-green-600 p-4 rounded-lg m-4">
        Border demo
      </div>
    </>
  );
}

export default App;
