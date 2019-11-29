组件化解耦开发

需求背景：随着项目业务的增多，项目代码变得越来越庞大。此时，需要将不同的业务剥离，分成一个个独立的Module，Module之间相互
依赖不可避免。为了项目结构更加的清晰和解耦，组件化开发变得十分重要了。

问题模块关系：  假如一个项目中有3个模块:app、模块A、模块B
    1. app模块可以依赖模块A、模块B，但是模块A无法依赖app模块。否则，会出现编译错误；
    2. 模块A和模块B相互独立；

    这时候需要创建一个公共的Module common ，app模块依赖公共模块common、模块A、模块B；
    模块A可以依赖公共模块common；
    模块B可以依赖公共模块common；
    模块A和模块B相互独立;

项目结构：
    模块:app、biz-login、biz-usercenter、shell
    1.app 依赖biz-login、biz-usercenter、shell
    2.biz-login依赖shell
    3.biz-usercenter依赖shell
    4.biz-login与biz-usercenter相互独立;




