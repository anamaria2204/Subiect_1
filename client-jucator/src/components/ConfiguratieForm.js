import React, { useState } from 'react';
import axios from 'axios';

function ConfiguratieForm() {
  const [linie, setLinie] = useState("");
  const [coloana, setColoana] = useState("");
  const [animal, setAnimal] = useState("");
  const [imagineUrl, setImagineUrl] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post("http://localhost:8080/api/configuratie", {
        linie: parseInt(linie),
        coloana: parseInt(coloana),
        animal,
        imagineUrl
      });

      alert("Configuratia a fost adăugată cu succes!");
      setLinie("");
      setColoana("");
      setAnimal("");
      setImagineUrl("");
    } catch (err) {
      alert("Eroare la salvarea configurației.");
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>Adaugă Configurație Nouă</h2>
      <form onSubmit={handleSubmit}>
        <input placeholder="Linie" type="number" value={linie} onChange={e => setLinie(e.target.value)} required />
        <input placeholder="Coloană" type="number" value={coloana} onChange={e => setColoana(e.target.value)} required />
        <input placeholder="Animal" type="text" value={animal} onChange={e => setAnimal(e.target.value)} required />
        <input placeholder="Imagine URL" type="text" value={imagineUrl} onChange={e => setImagineUrl(e.target.value)} required />
        <button type="submit">Adaugă</button>
      </form>
    </div>
  );
}

export default ConfiguratieForm;
