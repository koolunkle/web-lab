window.onload = setupRefresh;

function setupRefresh() {
    setTimeout(refreshPage, 5000);
}

function refreshPage() {
    globalThis.location = location.href;
}
