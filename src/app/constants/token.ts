
const tokenUrl = (code: string) => {
//   const redirectUri = `http://127.0.0.1:9090/appUser&grant_type=authorization_code&code=${code}`;
// return `http://127.0.0.1:9090/oauth2/authorize?response_type=code&client_id=client&scope=read&redirect_uri=${redirectUri}`;
  // const redirectUrl = `http://spring.io`;
    const redirectUrl = 'http://127.0.0.1:8080/home';
  return `http://localhost:8080/oauth2/token?client_id=client&redirect_uri=${redirectUrl}`;
};

export default tokenUrl;
