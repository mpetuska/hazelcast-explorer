config.resolve.modules.push("../../../../hazelcast-explorer/client/build/processedResources/js/main");
config.module.rules.push(
    {
        test: /\.(jpe?g|png|gif|svg)$/i,
        loader: 'file-loader',
        options: {
            esModule: false,
        },
    }
);
