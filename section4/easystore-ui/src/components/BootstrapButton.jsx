export default function BootstrapButton({ text, type }) {
  return <button className={`btn btn-${type}`}>{text}</button>;
}
