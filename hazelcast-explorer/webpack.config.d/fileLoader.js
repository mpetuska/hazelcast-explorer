config.module.rules.push(
    {
      test: /\.(svg|png)$/,
      loader: 'file-loader',
    },
);

config.resolve.modules.push('../../processedResources/frontend/main');

config.externals = ['aws-sdk', 'utf-8-validate', 'bufferutil'];