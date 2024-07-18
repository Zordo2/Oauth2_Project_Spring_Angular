const redirectUrl = () => {
//   const redirectUri = `http://127.0.0.1:9090/appUser`;
// return `http://127.0.0.1:9090/oauth2/authorize?response_type=code&client_id=client&scope=read&redirect_uri=${redirectUri}`;
// const redirectUri = 'http://spring.io';
  // const redirectUri = 'http://localhost:4200';
  const redirectUri = 'http://127.0.0.1:8080/home';
return `http://localhost:8080/oauth2/authorize?response_type=code&client_id=client&scope=read&redirect_uri=${redirectUri}`;

}

export default redirectUrl;
