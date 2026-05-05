export default function HydrateFallback() {
  return (
    <div className="flex items-center justify-center min-h-screen bg-normalbg dark:bg-darkbg">
      <span className="text-2xl font-semibold text-primary dark:text-light">
        Loading Application...
      </span>
    </div>
  );
}
