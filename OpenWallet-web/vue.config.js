module.exports = {
  devServer: {
    port: 8000,
    //    https: true,
    proxy: {
      "^/api": {
        target: "localhost:8080"
      }
    }
  }
};
