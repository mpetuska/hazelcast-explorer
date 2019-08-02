'use strict';

var webpack = require('webpack');

var config = {
    "mode": "development",
    "context": "/home/mpetuska/IdeaProjects/Personal/hazelcast-explorer-v3/build/kotlin-js-min/frontend/main",
    "entry": {
        "main": "./hazelcast-explorer"
    },
    "output": {
        "path": "/home/mpetuska/IdeaProjects/Personal/hazelcast-explorer-v3/build/bundle",
        "filename": "[name].bundle.js",
        "chunkFilename": "[id].bundle.js",
        "publicPath": "/"
    },
    "module": {
        "rules": [
            
        ]
    },
    "resolve": {
        "modules": [
            "kotlin-js-min/frontend/main",
            "resources/main",
            "/home/mpetuska/IdeaProjects/Personal/hazelcast-explorer-v3/build/node_modules",
            "node_modules"
        ]
    },
    "plugins": [
        
    ]
};

module.exports = config;

// from file /home/mpetuska/IdeaProjects/Personal/hazelcast-explorer-v3/webpack.config.d/fileLoader.js
config.module.rules.push(
    {
        test: /\.(svg|png)$/,
        loader: 'file-loader'
    }
);
// from file /home/mpetuska/IdeaProjects/Personal/hazelcast-explorer-v3/webpack.config.d/css.js
config.module.rules.push({
    test: /\.css$/,
    use: [
        require.resolve('style-loader'),
        {
            loader: require.resolve('css-loader'),
            options: {
                importLoaders: 1
            }
        }
    ]
});
