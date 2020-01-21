config.resolve.modules.push(
    config.output.path.substring(0, config.output.path.length - 13) +
    'processedResources/frontend/main');
config.output.filename = 'main.bundle.js';
if (!config.devServer && config.output) {
  config.mode = 'production';
  config.devtool = false;
  config.entry = [
    config.output.path.substring(0, config.output.path.length - 13) +
    'kotlin-js-min/frontend/main/' + config.output.filename];
  config.resolve.modules.push('.');
  config.resolve.modules.push('../../../../../build/js/node_modules');
  config.output.filename = 'main.bundle.js';
}
if (config.devServer) {
  config.devServer.watchOptions = {
    aggregateTimeout: 1000,
    poll: 500,
  };
  config.devServer.stats = {
    warnings: false,
  };
  config.devServer.clientLogLevel = 'error';
}
