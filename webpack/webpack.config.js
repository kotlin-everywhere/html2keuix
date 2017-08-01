var path = require('path');
var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');

//noinspection JSUnusedGlobalSymbols
module.exports = {
    entry: {
        app: './app/index.js',
        test: './test/index.js'
    },
    resolve: {
        modules: ['./node_modules', '../build/kotlin-javascript-dependencies', '../build/classes/main', '../build/classes/test'].map(function (s) {
            return path.resolve(__dirname, s);
        })
    },
    output: {
        path: __dirname + "/build",
        filename: "[name].[hash].bundle.js"
    },
    devServer: {
        hot: true
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './app/template.ejs', chunks: ['app']
        }),
        new HtmlWebpackPlugin({
            filename: 'test.html',
            template: './test/template.ejs', chunks: ['test']
        }),
        new webpack.HotModuleReplacementPlugin()
    ]
};
