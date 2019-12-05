window.onload = function () {
    updateClock();
};

function updateClock() {
    let now = new Date();
    let timeString = [String(now.getHours()).padStart(2, "0"), String(now.getMinutes()).padStart(2, "0")
        , String(now.getSeconds()).padStart(2, "0")].join(':');
    let dateString = [now.getDate(), now.getMonth() + 1, now.getFullYear()].join('.');
    document.getElementById('clock').innerHTML = [dateString, timeString].join('  ');
    setTimeout(updateClock, 5000);
}