import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Dashboard.css';

function Dashboard() {
  const query = new URLSearchParams(window.location.search);
  const nume = query.get("user");

  const [jocId, setJocId] = useState(null);
  const [mesaj, setMesaj] = useState("");
  const [disableGrid, setDisableGrid] = useState(false);
  const [imagineUrl, setImagineUrl] = useState(null);
  const [toateJocurile, setToateJocurile] = useState([]);
  const [jocPornit, setJocPornit] = useState(false);
  const [jocFinalizat, setJocFinalizat] = useState(false); // 🔸 starea nouă

  const rows = 3;
  const cols = 4;

  useEffect(() => {
    if (jocPornit || !nume) return;
    setJocPornit(true);

    const startJoc = async () => {
      try {
        const response = await axios.post(`http://localhost:8080/api/joc/start`, null, {
          params: { numeJucator: nume }
        });
        setJocId(response.data.id);
        setDisableGrid(false);
        setImagineUrl(null);
        setMesaj("");
        setJocFinalizat(false);
      } catch (error) {
        setMesaj("Eroare la pornirea jocului.");
      }
    };

    startJoc();
  }, [nume]);

  const fetchToateJocurile = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/joc/toate");
      setToateJocurile(response.data);
    } catch (error) {
      console.error("Eroare la preluarea jocurilor:", error);
    }
  };

  const handleCellClick = async (row, col) => {
    if (disableGrid || jocId === null) return;

    try {
      const response = await axios.post(`http://localhost:8080/api/mutare`, null, {
        params: {
          jocId: jocId,
          linie: row + 1,
          coloana: col + 1
        }
      });

      const { mesaj, imagineUrl } = response.data;
      setMesaj(mesaj);

      const isFinal = imagineUrl || mesaj.includes("Ai pierdut");
      if (isFinal) {
        setDisableGrid(true);
        setJocFinalizat(true); // 🔸 marchează jocul ca finalizat
        fetchToateJocurile();  // 🔸 actualizează tabelul
      }

    } catch (error) {
      setMesaj("Eroare la mutare.");
    }
  };

  const renderGrid = () => {
    const grid = [];
    for (let r = 0; r < rows; r++) {
      for (let c = 0; c < cols; c++) {
        grid.push(
          <div
            key={`${r}-${c}`}
            className={`cell ${disableGrid ? "disabled" : ""}`}
            onClick={() => handleCellClick(r, c)}
          />
        );
      }
    }
    return grid;
  };

  const renderTabelaJocuri = () => {
    return (
        <table border="1" style={{marginTop: '30px', width: '100%', textAlign: 'left'}}>
          <thead>
          <tr>
            <th>Jucător</th>
            <th>Animal</th>
            <th>Mutări efectuate</th>
            <th>Finalizat</th>
            <th>Poziție (linie, coloană)</th>
          </tr>
          </thead>
          <tbody>
          {toateJocurile.map((joc, index) => (
              <tr key={index}>
                <td>{joc.jucator || "N/A"}</td>
                <td>{joc.animal || "N/A"}</td>
                <td>
                  {joc.mutari_ramase}
                </td>
                <td>{joc.finalizat ? "✔️" : "❌"}</td>
                <td>{`(${joc.linie}, ${joc.coloana})`}</td>
              </tr>
          ))}
          </tbody>
        </table>
    );
  };

  return (
      <div style={{padding: "20px"}}>
        <h2>Bun venit, {nume}!</h2>

        <div className="grid">{renderGrid()}</div>

        <p style={{marginTop: "20px", fontWeight: "bold"}}>{mesaj}</p>

        {imagineUrl && (
            <div style={{marginTop: "20px"}}>
              <img src={imagineUrl} alt="Animal ghicit" style={{maxWidth: "300px"}}/>
            </div>
        )}

        {/* 🔸 tabelul apare DOAR după ce jocul e finalizat */}
      {jocFinalizat && renderTabelaJocuri()}
    </div>
  );
}

export default Dashboard;
