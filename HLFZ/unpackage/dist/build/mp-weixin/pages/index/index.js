(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["pages/index/index"],{"793b":function(t,e,n){"use strict";n.r(e);var a=n("87fb"),u=n.n(a);for(var i in a)"default"!==i&&function(t){n.d(e,t,function(){return a[t]})}(i);e["default"]=u.a},"87fb":function(t,e,n){"use strict";(function(t){Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var n={data:function(){return{indicatorDots:!0,autoplay:!0,interval:2e3,duration:500}},onLoad:function(){this.userInfo=this.checkLogin("../index/index","1")},methods:{getUser:function(){this.userId="aaaa",console.log("test",this.apiService+"/test/get"),t.request({url:this.apiService+"/test/get",method:"GET",data:{id:"bf583289-0d10-4d97-88ed-aaf6bf1a2058"},success:function(t){console.log("res",t)},fail:function(){console.log("fail","fail")}})}}};e.default=n}).call(this,n("543d")["default"])},9789:function(t,e,n){"use strict";var a=n("f67e"),u=n.n(a);u.a},a035:function(t,e,n){"use strict";(function(t){n("07d9"),n("921b");a(n("66fd"));var e=a(n("b88a"));function a(t){return t&&t.__esModule?t:{default:t}}t(e.default)}).call(this,n("543d")["createPage"])},b88a:function(t,e,n){"use strict";n.r(e);var a=n("bde9"),u=n("793b");for(var i in u)"default"!==i&&function(t){n.d(e,t,function(){return u[t]})}(i);n("9789");var o,c=n("f0c5"),r=Object(c["a"])(u["default"],a["b"],a["c"],!1,null,null,null,!1,a["a"],o);e["default"]=r.exports},bde9:function(t,e,n){"use strict";var a,u=function(){var t=this,e=t.$createElement;t._self._c},i=[];n.d(e,"b",function(){return u}),n.d(e,"c",function(){return i}),n.d(e,"a",function(){return a})},f67e:function(t,e,n){}},[["a035","common/runtime","common/vendor"]]]);