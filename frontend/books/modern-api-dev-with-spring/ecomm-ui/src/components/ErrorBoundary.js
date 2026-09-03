import { Component } from "react";

class ErrorBoundary extends Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError() {
    return { hasError: true };
  }

  componentDidCatch(error, errorInfo) {
    console.error(error, errorInfo);
  }

  render() {
    if (this.state.hasError) {
      return (
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16 text-center">
          <h1 className="text-2xl font-bold text-gray-900">
            문제가 발생했습니다.
          </h1>
          <p className="mt-2 text-gray-500">
            페이지를 표시하는 중 오류가 발생했습니다. 새로고침 후 다시
            시도해주세요.
          </p>
          <button
            onClick={() => window.location.assign("/")}
            className="mt-6 bg-indigo-500 hover:bg-indigo-600 px-5 py-2 text-sm text-white uppercase font-semibold"
          >
            홈으로 이동
          </button>
        </div>
      );
    }

    return this.props.children;
  }
}

export default ErrorBoundary;
