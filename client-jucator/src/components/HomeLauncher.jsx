import { useEffect } from 'react';

function HomeLauncher() {
  useEffect(() => {
    window.open('/login', '_blank');
    window.open('/configuratie', '_blank');
  }, []);

  return <p>Se încarcă aplicația...</p>;
}

export default HomeLauncher;
