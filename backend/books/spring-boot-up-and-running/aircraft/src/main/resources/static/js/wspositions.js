const socket = new WebSocket('ws://' + globalThis.location.host + '/ws');

socket.onopen = function () {
    console.log('WebSocket connection is open for business, bienvenidos!');
};

socket.onmessage = function (event) {
    let aircrafts;
    try {
        aircrafts = JSON.parse(event.data);
    } catch (e) {
        console.error('Failed to parse WebSocket message as JSON:', e, 'Raw data:', event.data);
        document.getElementById("positions-display").innerText = "Error: Invalid data format received";
        return;
    }

    if (!Array.isArray(aircrafts)) {
        console.warn('Received JSON is not an array:', aircrafts);
        document.getElementById("positions-display").innerText = "Error: Received data is not a list";
        return;
    }

    let text = "";
    aircrafts.forEach(ac => {
        text += `Callsign: ${ac.callsign || 'N/A'}, ` +
                `Reg: ${ac.reg || 'N/A'}, ` +
                `Type: ${ac.type || 'N/A'}, ` +
                `Alt: ${ac.altitude || 0}, ` +
                `Lat: ${ac.lat || 0}, ` +
                `Lon: ${ac.lon || 0}\n`;
    });
    document.getElementById("positions-display").innerText = text;
};

socket.onclose = function () {
    console.log('WebSocket connection closed');
};

socket.onerror = function (error) {
    console.error('WebSocket error:', error);
};
