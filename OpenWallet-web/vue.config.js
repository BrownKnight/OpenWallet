module.exports = {
  devServer: {
    port: 8000,
    //    https: true,
    proxy: {
      "^/api": {
        target: "http://localhost:8080"
      }
    }
  }
};
