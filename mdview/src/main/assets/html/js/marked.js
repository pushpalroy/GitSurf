/**
 * marked - a markdown parser
 * Copyright (c) 2011-2020, Christopher Jeffrey. (MIT Licensed)
 * https://github.com/markedjs/marked
 */
!function(e,t){"object"==typeof exports&&"undefined"!=typeof module?module.exports=t():"function"==typeof define&&define.amd?define(t):(e=e||self).marked=t()}(this,function(){"use strict";function s(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}function r(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}function g(e){var t=0;if("undefined"!=typeof Symbol&&null!=e[Symbol.iterator])return(t=e[Symbol.iterator]()).next.bind(t);if(Array.isArray(e)||(e=function(e,t){if(e){if("string"==typeof e)return r(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?r(e,t):void 0}}(e)))return function(){return t>=e.length?{done:!0}:{done:!1,value:e[t++]}};throw new TypeError("Invalid attempt to iterate non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}function n(e){return c[e]}var e,t=(function(t){function e(){return{baseUrl:null,breaks:!1,gfm:!0,headerIds:!0,headerPrefix:"",highlight:null,langPrefix:"language-",mangle:!0,pedantic:!1,renderer:null,sanitize:!1,sanitizer:null,silent:!1,smartLists:!1,smartypants:!1,tokenizer:null,walkTokens:null,xhtml:!1}}t.exports={defaults:e(),getDefaults:e,changeDefaults:function(e){t.exports.defaults=e}}}(e={exports:{}}),e.exports),i=(t.defaults,t.getDefaults,t.changeDefaults,/[&<>"']/),a=/[&<>"']/g,l=/[<>"']|&(?!#?\w+;)/,o=/[<>"']|&(?!#?\w+;)/g,c={"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#39;"};var h=/&(#(?:\d+)|(?:#x[0-9A-Fa-f]+)|(?:\w+));?/gi;function u(e){return e.replace(h,function(e,t){return"colon"===(t=t.toLowerCase())?":":"#"===t.charAt(0)?"x"===t.charAt(1)?String.fromCharCode(parseInt(t.substring(2),16)):String.fromCharCode(+t.substring(1)):""})}var p=/(^|[^\[])\^/g;var f=/[^\w:]/g,d=/^$|^[a-z][a-z0-9+.-]*:|^[?#]/i;var k={},b=/^[^:]+:\/*[^/]*$/,m=/^([^:]+:)[\s\S]*$/,x=/^([^:]+:\/*[^/]*)[\s\S]*$/;function w(e,t){k[" "+e]||(b.test(e)?k[" "+e]=e+"/":k[" "+e]=v(e,"/",!0));var n=-1===(e=k[" "+e]).indexOf(":");return"//"===t.substring(0,2)?n?t:e.replace(m,"$1")+t:"/"===t.charAt(0)?n?t:e.replace(x,"$1")+t:e+t}function v(e,t,n){var r=e.length;if(0===r)return"";for(var i=0;i<r;){var s=e.charAt(r-i-1);if(s!==t||n){if(s===t||!n)break;i++}else i++}return e.substr(0,r-i)}var y=function(e,t){if(t){if(i.test(e))return e.replace(a,n)}else if(l.test(e))return e.replace(o,n);return e},_=u,z=function(n,e){n=n.source||n,e=e||"";var r={replace:function(e,t){return t=(t=t.source||t).replace(p,"$1"),n=n.replace(e,t),r},getRegex:function(){return new RegExp(n,e)}};return r},$=function(e,t,n){if(e){var r;try{r=decodeURIComponent(u(n)).replace(f,"").toLowerCase()}catch(e){return null}if(0===r.indexOf("javascript:")||0===r.indexOf("vbscript:")||0===r.indexOf("data:"))return null}t&&!d.test(n)&&(n=w(t,n));try{n=encodeURI(n).replace(/%25/g,"%")}catch(e){return null}return n},S={exec:function(){}},A=function(e){for(var t,n,r=1;r<arguments.length;r++)for(n in t=arguments[r])Object.prototype.hasOwnProperty.call(t,n)&&(e[n]=t[n]);return e},T=function(e,t){var n=e.replace(/\|/g,function(e,t,n){for(var r=!1,i=t;0<=--i&&"\\"===n[i];)r=!r;return r?"|":" |"}).split(/ \|/),r=0;if(n.length>t)n.splice(t);else for(;n.length<t;)n.push("");for(;r<n.length;r++)n[r]=n[r].trim().replace(/\\\|/g,"|");return n},R=function(e,t){if(-1===e.indexOf(t[1]))return-1;for(var n=e.length,r=0,i=0;i<n;i++)if("\\"===e[i])i++;else if(e[i]===t[0])r++;else if(e[i]===t[1]&&--r<0)return i;return-1},Z=function(e){e&&e.sanitize&&!e.silent&&console.warn("marked(): sanitize and sanitizer parameters are deprecated since version 0.7.0, should not be used and will be removed in the future. Read more here: https://marked.js.org/#/USING_ADVANCED.md#options")},q=t.defaults,I=v,O=T,C=y,j=R;function E(e,t,n){var r=t.href,i=t.title?C(t.title):null,s=e[1].replace(/\\([\[\]])/g,"$1");return"!"!==e[0].charAt(0)?{type:"link",raw:n,href:r,title:i,text:s}:{type:"image",raw:n,href:r,title:i,text:C(s)}}var D=function(){function e(e){this.options=e||q}var t=e.prototype;return t.space=function(e){var t=this.rules.block.newline.exec(e);if(t)return 1<t[0].length?{type:"space",raw:t[0]}:{raw:"\n"}},t.code=function(e,t){var n=this.rules.block.code.exec(e);if(n){var r=t[t.length-1];if(r&&"paragraph"===r.type)return{raw:n[0],text:n[0].trimRight()};var i=n[0].replace(/^ {4}/gm,"");return{type:"code",raw:n[0],codeBlockStyle:"indented",text:this.options.pedantic?i:I(i,"\n")}}},t.fences=function(e){var t=this.rules.block.fences.exec(e);if(t){var n=t[0],r=function(e,t){var n=e.match(/^(\s+)(?:```)/);if(null===n)return t;var r=n[1];return t.split("\n").map(function(e){var t=e.match(/^\s+/);return null!==t&&t[0].length>=r.length?e.slice(r.length):e}).join("\n")}(n,t[3]||"");return{type:"code",raw:n,lang:t[2]?t[2].trim():t[2],text:r}}},t.heading=function(e){var t=this.rules.block.heading.exec(e);if(t)return{type:"heading",raw:t[0],depth:t[1].length,text:t[2]}},t.nptable=function(e){var t=this.rules.block.nptable.exec(e);if(t){var n={type:"table",header:O(t[1].replace(/^ *| *\| *$/g,"")),align:t[2].replace(/^ *|\| *$/g,"").split(/ *\| */),cells:t[3]?t[3].replace(/\n$/,"").split("\n"):[],raw:t[0]};if(n.header.length===n.align.length){for(var r=n.align.length,i=0;i<r;i++)/^ *-+: *$/.test(n.align[i])?n.align[i]="right":/^ *:-+: *$/.test(n.align[i])?n.align[i]="center":/^ *:-+ *$/.test(n.align[i])?n.align[i]="left":n.align[i]=null;for(r=n.cells.length,i=0;i<r;i++)n.cells[i]=O(n.cells[i],n.header.length);return n}}},t.hr=function(e){var t=this.rules.block.hr.exec(e);if(t)return{type:"hr",raw:t[0]}},t.blockquote=function(e){var t=this.rules.block.blockquote.exec(e);if(t){var n=t[0].replace(/^ *> ?/gm,"");return{type:"blockquote",raw:t[0],text:n}}},t.list=function(e){var t=this.rules.block.list.exec(e);if(t){for(var n,r,i,s,a,l,o,c=t[0],h=t[2],u=1<h.length,p=")"===h[h.length-1],g={type:"list",raw:c,ordered:u,start:u?+h.slice(0,-1):"",loose:!1,items:[]},f=t[0].match(this.rules.block.item),d=!1,k=f.length,b=0;b<k;b++)r=(c=n=f[b]).length,~(n=n.replace(/^ *([*+-]|\d+[.)]) */,"")).indexOf("\n ")&&(r-=n.length,n=this.options.pedantic?n.replace(/^ {1,4}/gm,""):n.replace(new RegExp("^ {1,"+r+"}","gm"),"")),b!==k-1&&(i=this.rules.block.bullet.exec(f[b+1])[0],(u?1===i.length||!p&&")"===i[i.length-1]:1<i.length||this.options.smartLists&&i!==h)&&(s=f.slice(b+1).join("\n"),g.raw=g.raw.substring(0,g.raw.length-s.length),b=k-1)),a=d||/\n\n(?!\s*$)/.test(n),b!==k-1&&(d="\n"===n.charAt(n.length-1),a=a||d),a&&(g.loose=!0),o=void 0,(l=/^\[[ xX]\] /.test(n))&&(o=" "!==n[1],n=n.replace(/^\[[ xX]\] +/,"")),g.items.push({type:"list_item",raw:c,task:l,checked:o,loose:a,text:n});return g}},t.html=function(e){var t=this.rules.block.html.exec(e);if(t)return{type:this.options.sanitize?"paragraph":"html",raw:t[0],pre:!this.options.sanitizer&&("pre"===t[1]||"script"===t[1]||"style"===t[1]),text:this.options.sanitize?this.options.sanitizer?this.options.sanitizer(t[0]):C(t[0]):t[0]}},t.def=function(e){var t=this.rules.block.def.exec(e);if(t)return t[3]&&(t[3]=t[3].substring(1,t[3].length-1)),{tag:t[1].toLowerCase().replace(/\s+/g," "),raw:t[0],href:t[2],title:t[3]}},t.table=function(e){var t=this.rules.block.table.exec(e);if(t){var n={type:"table",header:O(t[1].replace(/^ *| *\| *$/g,"")),align:t[2].replace(/^ *|\| *$/g,"").split(/ *\| */),cells:t[3]?t[3].replace(/\n$/,"").split("\n"):[]};if(n.header.length===n.align.length){n.raw=t[0];for(var r=n.align.length,i=0;i<r;i++)/^ *-+: *$/.test(n.align[i])?n.align[i]="right":/^ *:-+: *$/.test(n.align[i])?n.align[i]="center":/^ *:-+ *$/.test(n.align[i])?n.align[i]="left":n.align[i]=null;for(r=n.cells.length,i=0;i<r;i++)n.cells[i]=O(n.cells[i].replace(/^ *\| *| *\| *$/g,""),n.header.length);return n}}},t.lheading=function(e){var t=this.rules.block.lheading.exec(e);if(t)return{type:"heading",raw:t[0],depth:"="===t[2].charAt(0)?1:2,text:t[1]}},t.paragraph=function(e){var t=this.rules.block.paragraph.exec(e);if(t)return{type:"paragraph",raw:t[0],text:"\n"===t[1].charAt(t[1].length-1)?t[1].slice(0,-1):t[1]}},t.text=function(e,t){var n=this.rules.block.text.exec(e);if(n){var r=t[t.length-1];return r&&"text"===r.type?{raw:n[0],text:n[0]}:{type:"text",raw:n[0],text:n[0]}}},t.escape=function(e){var t=this.rules.inline.escape.exec(e);if(t)return{type:"escape",raw:t[0],text:C(t[1])}},t.tag=function(e,t,n){var r=this.rules.inline.tag.exec(e);if(r)return!t&&/^<a /i.test(r[0])?t=!0:t&&/^<\/a>/i.test(r[0])&&(t=!1),!n&&/^<(pre|code|kbd|script)(\s|>)/i.test(r[0])?n=!0:n&&/^<\/(pre|code|kbd|script)(\s|>)/i.test(r[0])&&(n=!1),{type:this.options.sanitize?"text":"html",raw:r[0],inLink:t,inRawBlock:n,text:this.options.sanitize?this.options.sanitizer?this.options.sanitizer(r[0]):C(r[0]):r[0]}},t.link=function(e){var t=this.rules.inline.link.exec(e);if(t){var n,r=j(t[2],"()");-1<r&&(n=(0===t[0].indexOf("!")?5:4)+t[1].length+r,t[2]=t[2].substring(0,r),t[0]=t[0].substring(0,n).trim(),t[3]="");var i,s=t[2],a="";return a=this.options.pedantic?(i=/^([^'"]*[^\s])\s+(['"])(.*)\2/.exec(s),i?(s=i[1],i[3]):""):t[3]?t[3].slice(1,-1):"",E(t,{href:(s=s.trim().replace(/^<([\s\S]*)>$/,"$1"))?s.replace(this.rules.inline._escapes,"$1"):s,title:a?a.replace(this.rules.inline._escapes,"$1"):a},t[0])}},t.reflink=function(e,t){var n;if((n=this.rules.inline.reflink.exec(e))||(n=this.rules.inline.nolink.exec(e))){var r=(n[2]||n[1]).replace(/\s+/g," ");if((r=t[r.toLowerCase()])&&r.href)return E(n,r,n[0]);var i=n[0].charAt(0);return{type:"text",raw:i,text:i}}},t.strong=function(e){var t=this.rules.inline.strong.exec(e);if(t)return{type:"strong",raw:t[0],text:t[4]||t[3]||t[2]||t[1]}},t.em=function(e){var t=this.rules.inline.em.exec(e);if(t)return{type:"em",raw:t[0],text:t[6]||t[5]||t[4]||t[3]||t[2]||t[1]}},t.codespan=function(e){var t=this.rules.inline.code.exec(e);if(t){var n=t[2].replace(/\n/g," "),r=/[^ ]/.test(n),i=n.startsWith(" ")&&n.endsWith(" ");return r&&i&&(n=n.substring(1,n.length-1)),n=C(n,!0),{type:"codespan",raw:t[0],text:n}}},t.br=function(e){var t=this.rules.inline.br.exec(e);if(t)return{type:"br",raw:t[0]}},t.del=function(e){var t=this.rules.inline.del.exec(e);if(t)return{type:"del",raw:t[0],text:t[1]}},t.autolink=function(e,t){var n=this.rules.inline.autolink.exec(e);if(n){var r,i="@"===n[2]?"mailto:"+(r=C(this.options.mangle?t(n[1]):n[1])):r=C(n[1]);return{type:"link",raw:n[0],text:r,href:i,tokens:[{type:"text",raw:r,text:r}]}}},t.url=function(e,t){var n,r,i,s;if(n=this.rules.inline.url.exec(e)){if("@"===n[2])i="mailto:"+(r=C(this.options.mangle?t(n[0]):n[0]));else{for(;s=n[0],n[0]=this.rules.inline._backpedal.exec(n[0])[0],s!==n[0];);r=C(n[0]),i="www."===n[1]?"http://"+r:r}return{type:"link",raw:n[0],text:r,href:i,tokens:[{type:"text",raw:r,text:r}]}}},t.inlineText=function(e,t,n){var r=this.rules.inline.text.exec(e);if(r){var i=t?this.options.sanitize?this.options.sanitizer?this.options.sanitizer(r[0]):C(r[0]):r[0]:C(this.options.smartypants?n(r[0]):r[0]);return{type:"text",raw:r[0],text:i}}},e}(),L=S,P=z,U=A,B={newline:/^\n+/,code:/^( {4}[^\n]+\n*)+/,fences:/^ {0,3}(`{3,}(?=[^`\n]*\n)|~{3,})([^\n]*)\n(?:|([\s\S]*?)\n)(?: {0,3}\1[~`]* *(?:\n+|$)|$)/,hr:/^ {0,3}((?:- *){3,}|(?:_ *){3,}|(?:\* *){3,})(?:\n+|$)/,heading:/^ {0,3}(#{1,6}) +([^\n]*?)(?: +#+)? *(?:\n+|$)/,blockquote:/^( {0,3}> ?(paragraph|[^\n]*)(?:\n|$))+/,list:/^( {0,3})(bull) [\s\S]+?(?:hr|def|\n{2,}(?! )(?!\1bull )\n*|\s*$)/,html:"^ {0,3}(?:<(script|pre|style)[\\s>][\\s\\S]*?(?:</\\1>[^\\n]*\\n+|$)|comment[^\\n]*(\\n+|$)|<\\?[\\s\\S]*?\\?>\\n*|<![A-Z][\\s\\S]*?>\\n*|<!\\[CDATA\\[[\\s\\S]*?\\]\\]>\\n*|</?(tag)(?: +|\\n|/?>)[\\s\\S]*?(?:\\n{2,}|$)|<(?!script|pre|style)([a-z][\\w-]*)(?:attribute)*? */?>(?=[ \\t]*(?:\\n|$))[\\s\\S]*?(?:\\n{2,}|$)|</(?!script|pre|style)[a-z][\\w-]*\\s*>(?=[ \\t]*(?:\\n|$))[\\s\\S]*?(?:\\n{2,}|$))",def:/^ {0,3}\[(label)\]: *\n? *<?([^\s>]+)>?(?:(?: +\n? *| *\n *)(title))? *(?:\n+|$)/,nptable:L,table:L,lheading:/^([^\n]+)\n {0,3}(=+|-+) *(?:\n+|$)/,_paragraph:/^([^\n]+(?:\n(?!hr|heading|lheading|blockquote|fences|list|html)[^\n]+)*)/,text:/^[^\n]+/,_label:/(?!\s*\])(?:\\[\[\]]|[^\[\]])+/,_title:/(?:"(?:\\"?|[^"\\])*"|'[^'\n]*(?:\n[^'\n]+)*\n?'|\([^()]*\))/};B.def=P(B.def).replace("label",B._label).replace("title",B._title).getRegex(),B.bullet=/(?:[*+-]|\d{1,9}[.)])/,B.item=/^( *)(bull) ?[^\n]*(?:\n(?!\1bull ?)[^\n]*)*/,B.item=P(B.item,"gm").replace(/bull/g,B.bullet).getRegex(),B.list=P(B.list).replace(/bull/g,B.bullet).replace("hr","\\n+(?=\\1?(?:(?:- *){3,}|(?:_ *){3,}|(?:\\* *){3,})(?:\\n+|$))").replace("def","\\n+(?="+B.def.source+")").getRegex(),B._tag="address|article|aside|base|basefont|blockquote|body|caption|center|col|colgroup|dd|details|dialog|dir|div|dl|dt|fieldset|figcaption|figure|footer|form|frame|frameset|h[1-6]|head|header|hr|html|iframe|legend|li|link|main|menu|menuitem|meta|nav|noframes|ol|optgroup|option|p|param|section|source|summary|table|tbody|td|tfoot|th|thead|title|tr|track|ul",B._comment=/<!--(?!-?>)[\s\S]*?-->/,B.html=P(B.html,"i").replace("comment",B._comment).replace("tag",B._tag).replace("attribute",/ +[a-zA-Z:_][\w.:-]*(?: *= *"[^"\n]*"| *= *'[^'\n]*'| *= *[^\s"'=<>`]+)?/).getRegex(),B.paragraph=P(B._paragraph).replace("hr",B.hr).replace("heading"," {0,3}#{1,6} ").replace("|lheading","").replace("blockquote"," {0,3}>").replace("fences"," {0,3}(?:`{3,}(?=[^`\\n]*\\n)|~{3,})[^\\n]*\\n").replace("list"," {0,3}(?:[*+-]|1[.)]) ").replace("html","</?(?:tag)(?: +|\\n|/?>)|<(?:script|pre|style|!--)").replace("tag",B._tag).getRegex(),B.blockquote=P(B.blockquote).replace("paragraph",B.paragraph).getRegex(),B.normal=U({},B),B.gfm=U({},B.normal,{nptable:"^ *([^|\\n ].*\\|.*)\\n *([-:]+ *\\|[-| :]*)(?:\\n((?:(?!\\n|hr|heading|blockquote|code|fences|list|html).*(?:\\n|$))*)\\n*|$)",table:"^ *\\|(.+)\\n *\\|?( *[-:]+[-| :]*)(?:\\n *((?:(?!\\n|hr|heading|blockquote|code|fences|list|html).*(?:\\n|$))*)\\n*|$)"}),B.gfm.nptable=P(B.gfm.nptable).replace("hr",B.hr).replace("heading"," {0,3}#{1,6} ").replace("blockquote"," {0,3}>").replace("code"," {4}[^\\n]").replace("fences"," {0,3}(?:`{3,}(?=[^`\\n]*\\n)|~{3,})[^\\n]*\\n").replace("list"," {0,3}(?:[*+-]|1[.)]) ").replace("html","</?(?:tag)(?: +|\\n|/?>)|<(?:script|pre|style|!--)").replace("tag",B._tag).getRegex(),B.gfm.table=P(B.gfm.table).replace("hr",B.hr).replace("heading"," {0,3}#{1,6} ").replace("blockquote"," {0,3}>").replace("code"," {4}[^\\n]").replace("fences"," {0,3}(?:`{3,}(?=[^`\\n]*\\n)|~{3,})[^\\n]*\\n").replace("list"," {0,3}(?:[*+-]|1[.)]) ").replace("html","</?(?:tag)(?: +|\\n|/?>)|<(?:script|pre|style|!--)").replace("tag",B._tag).getRegex(),B.pedantic=U({},B.normal,{html:P("^ *(?:comment *(?:\\n|\\s*$)|<(tag)[\\s\\S]+?</\\1> *(?:\\n{2,}|\\s*$)|<tag(?:\"[^\"]*\"|'[^']*'|\\s[^'\"/>\\s]*)*?/?> *(?:\\n{2,}|\\s*$))").replace("comment",B._comment).replace(/tag/g,"(?!(?:a|em|strong|small|s|cite|q|dfn|abbr|data|time|code|var|samp|kbd|sub|sup|i|b|u|mark|ruby|rt|rp|bdi|bdo|span|br|wbr|ins|del|img)\\b)\\w+(?!:|[^\\w\\s@]*@)\\b").getRegex(),def:/^ *\[([^\]]+)\]: *<?([^\s>]+)>?(?: +(["(][^\n]+[")]))? *(?:\n+|$)/,heading:/^ *(#{1,6}) *([^\n]+?) *(?:#+ *)?(?:\n+|$)/,fences:L,paragraph:P(B.normal._paragraph).replace("hr",B.hr).replace("heading"," *#{1,6} *[^\n]").replace("lheading",B.lheading).replace("blockquote"," {0,3}>").replace("|fences","").replace("|list","").replace("|html","").getRegex()});var F={escape:/^\\([!"#$%&'()*+,\-./:;<=>?@\[\]\\^_`{|}~])/,autolink:/^<(scheme:[^\s\x00-\x1f<>]*|email)>/,url:L,tag:"^comment|^</[a-zA-Z][\\w:-]*\\s*>|^<[a-zA-Z][\\w-]*(?:attribute)*?\\s*/?>|^<\\?[\\s\\S]*?\\?>|^<![a-zA-Z]+\\s[\\s\\S]*?>|^<!\\[CDATA\\[[\\s\\S]*?\\]\\]>",link:/^!?\[(label)\]\(\s*(href)(?:\s+(title))?\s*\)/,reflink:/^!?\[(label)\]\[(?!\s*\])((?:\\[\[\]]?|[^\[\]\\])+)\]/,nolink:/^!?\[(?!\s*\])((?:\[[^\[\]]*\]|\\[\[\]]|[^\[\]])*)\](?:\[\])?/,strong:/^__([^\s_])__(?!_)|^\*\*([^\s*])\*\*(?!\*)|^__([^\s][\s\S]*?[^\s])__(?!_)|^\*\*([^\s][\s\S]*?[^\s])\*\*(?!\*)/,em:/^_([^\s_])_(?!_)|^_([^\s_<][\s\S]*?[^\s_])_(?!_|[^\s,punctuation])|^_([^\s_<][\s\S]*?[^\s])_(?!_|[^\s,punctuation])|^\*([^\s*<\[])\*(?!\*)|^\*([^\s<"][\s\S]*?[^\s\[\*])\*(?![\]`punctuation])|^\*([^\s*"<\[][\s\S]*[^\s])\*(?!\*)/,code:/^(`+)([^`]|[^`][\s\S]*?[^`])\1(?!`)/,br:/^( {2,}|\\)\n(?!\s*$)/,del:L,text:/^(`+|[^`])(?:[\s\S]*?(?:(?=[\\<!\[`*]|\b_|$)|[^ ](?= {2,}\n))|(?= {2,}\n))/,_punctuation:"!\"#$%&'()*+\\-./:;<=>?@\\[^_{|}~"};F.em=P(F.em).replace(/punctuation/g,F._punctuation).getRegex(),F._escapes=/\\([!"#$%&'()*+,\-./:;<=>?@\[\]\\^_`{|}~])/g,F._scheme=/[a-zA-Z][a-zA-Z0-9+.-]{1,31}/,F._email=/[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+(@)[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)+(?![-_])/,F.autolink=P(F.autolink).replace("scheme",F._scheme).replace("email",F._email).getRegex(),F._attribute=/\s+[a-zA-Z:_][\w.:-]*(?:\s*=\s*"[^"]*"|\s*=\s*'[^']*'|\s*=\s*[^\s"'=<>`]+)?/,F.tag=P(F.tag).replace("comment",B._comment).replace("attribute",F._attribute).getRegex(),F._label=/(?:\[(?:\\.|[^\[\]\\])*\]|\\.|`[^`]*`|[^\[\]\\`])*?/,F._href=/<(?:\\[<>]?|[^\s<>\\])*>|[^\s\x00-\x1f]*/,F._title=/"(?:\\"?|[^"\\])*"|'(?:\\'?|[^'\\])*'|\((?:\\\)?|[^)\\])*\)/,F.link=P(F.link).replace("label",F._label).replace("href",F._href).replace("title",F._title).getRegex(),F.reflink=P(F.reflink).replace("label",F._label).getRegex(),F.normal=U({},F),F.pedantic=U({},F.normal,{strong:/^__(?=\S)([\s\S]*?\S)__(?!_)|^\*\*(?=\S)([\s\S]*?\S)\*\*(?!\*)/,em:/^_(?=\S)([\s\S]*?\S)_(?!_)|^\*(?=\S)([\s\S]*?\S)\*(?!\*)/,link:P(/^!?\[(label)\]\((.*?)\)/).replace("label",F._label).getRegex(),reflink:P(/^!?\[(label)\]\s*\[([^\]]*)\]/).replace("label",F._label).getRegex()}),F.gfm=U({},F.normal,{escape:P(F.escape).replace("])","~|])").getRegex(),_extended_email:/[A-Za-z0-9._+-]+(@)[a-zA-Z0-9-_]+(?:\.[a-zA-Z0-9-_]*[a-zA-Z0-9])+(?![-_])/,url:/^((?:ftp|https?):\/\/|www\.)(?:[a-zA-Z0-9\-]+\.?)+[^\s<]*|^email/,_backpedal:/(?:[^?!.,:;*_~()&]+|\([^)]*\)|&(?![a-zA-Z0-9]+;$)|[?!.,:;*_~)]+(?!$))+/,del:/^~+(?=\S)([\s\S]*?\S)~+/,text:/^(`+|[^`])(?:[\s\S]*?(?:(?=[\\<!\[`*~]|\b_|https?:\/\/|ftp:\/\/|www\.|$)|[^ ](?= {2,}\n)|[^a-zA-Z0-9.!#$%&'*+\/=?_`{\|}~-](?=[a-zA-Z0-9.!#$%&'*+\/=?_`{\|}~-]+@))|(?= {2,}\n|[a-zA-Z0-9.!#$%&'*+\/=?_`{\|}~-]+@))/}),F.gfm.url=P(F.gfm.url,"i").replace("email",F.gfm._extended_email).getRegex(),F.breaks=U({},F.gfm,{br:P(F.br).replace("{2,}","*").getRegex(),text:P(F.gfm.text).replace("\\b_","\\b_| {2,}\\n").replace(/\{2,\}/g,"*").getRegex()});var M={block:B,inline:F},N=t.defaults,W=M.block,X=M.inline;function G(e){return e.replace(/---/g,"—").replace(/--/g,"–").replace(/(^|[-\u2014/(\[{"\s])'/g,"$1‘").replace(/'/g,"’").replace(/(^|[-\u2014/(\[{\u2018\s])"/g,"$1“").replace(/"/g,"”").replace(/\.{3}/g,"…")}function V(e){for(var t,n="",r=e.length,i=0;i<r;i++)t=e.charCodeAt(i),.5<Math.random()&&(t="x"+t.toString(16)),n+="&#"+t+";";return n}var H=function(){function n(e){this.tokens=[],this.tokens.links=Object.create(null),this.options=e||N,this.options.tokenizer=this.options.tokenizer||new D,this.tokenizer=this.options.tokenizer,this.tokenizer.options=this.options;var t={block:W.normal,inline:X.normal};this.options.pedantic?(t.block=W.pedantic,t.inline=X.pedantic):this.options.gfm&&(t.block=W.gfm,this.options.breaks?t.inline=X.breaks:t.inline=X.gfm),this.tokenizer.rules=t}n.lex=function(e,t){return new n(t).lex(e)};var e,t,r,i=n.prototype;return i.lex=function(e){return e=e.replace(/\r\n|\r/g,"\n").replace(/\t/g,"    "),this.blockTokens(e,this.tokens,!0),this.inline(this.tokens),this.tokens},i.blockTokens=function(e,t,n){var r,i,s,a;for(void 0===t&&(t=[]),void 0===n&&(n=!0),e=e.replace(/^ +$/gm,"");e;)if(r=this.tokenizer.space(e))e=e.substring(r.raw.length),r.type&&t.push(r);else if(r=this.tokenizer.code(e,t))e=e.substring(r.raw.length),r.type?t.push(r):((a=t[t.length-1]).raw+="\n"+r.raw,a.text+="\n"+r.text);else if(r=this.tokenizer.fences(e))e=e.substring(r.raw.length),t.push(r);else if(r=this.tokenizer.heading(e))e=e.substring(r.raw.length),t.push(r);else if(r=this.tokenizer.nptable(e))e=e.substring(r.raw.length),t.push(r);else if(r=this.tokenizer.hr(e))e=e.substring(r.raw.length),t.push(r);else if(r=this.tokenizer.blockquote(e))e=e.substring(r.raw.length),r.tokens=this.blockTokens(r.text,[],n),t.push(r);else if(r=this.tokenizer.list(e)){for(e=e.substring(r.raw.length),s=r.items.length,i=0;i<s;i++)r.items[i].tokens=this.blockTokens(r.items[i].text,[],!1);t.push(r)}else if(r=this.tokenizer.html(e))e=e.substring(r.raw.length),t.push(r);else if(n&&(r=this.tokenizer.def(e)))e=e.substring(r.raw.length),this.tokens.links[r.tag]||(this.tokens.links[r.tag]={href:r.href,title:r.title});else if(r=this.tokenizer.table(e))e=e.substring(r.raw.length),t.push(r);else if(r=this.tokenizer.lheading(e))e=e.substring(r.raw.length),t.push(r);else if(n&&(r=this.tokenizer.paragraph(e)))e=e.substring(r.raw.length),t.push(r);else if(r=this.tokenizer.text(e,t))e=e.substring(r.raw.length),r.type?t.push(r):((a=t[t.length-1]).raw+="\n"+r.raw,a.text+="\n"+r.text);else if(e){var l="Infinite loop on byte: "+e.charCodeAt(0);if(this.options.silent){console.error(l);break}throw new Error(l)}return t},i.inline=function(e){for(var t,n,r,i,s,a=e.length,l=0;l<a;l++)switch((s=e[l]).type){case"paragraph":case"text":case"heading":s.tokens=[],this.inlineTokens(s.text,s.tokens);break;case"table":for(s.tokens={header:[],cells:[]},r=s.header.length,t=0;t<r;t++)s.tokens.header[t]=[],this.inlineTokens(s.header[t],s.tokens.header[t]);for(r=s.cells.length,t=0;t<r;t++)for(i=s.cells[t],s.tokens.cells[t]=[],n=0;n<i.length;n++)s.tokens.cells[t][n]=[],this.inlineTokens(i[n],s.tokens.cells[t][n]);break;case"blockquote":this.inline(s.tokens);break;case"list":for(r=s.items.length,t=0;t<r;t++)this.inline(s.items[t].tokens)}return e},i.inlineTokens=function(e,t,n,r){var i;for(void 0===t&&(t=[]),void 0===n&&(n=!1),void 0===r&&(r=!1);e;)if(i=this.tokenizer.escape(e))e=e.substring(i.raw.length),t.push(i);else if(i=this.tokenizer.tag(e,n,r))e=e.substring(i.raw.length),n=i.inLink,r=i.inRawBlock,t.push(i);else if(i=this.tokenizer.link(e))e=e.substring(i.raw.length),"link"===i.type&&(i.tokens=this.inlineTokens(i.text,[],!0,r)),t.push(i);else if(i=this.tokenizer.reflink(e,this.tokens.links))e=e.substring(i.raw.length),"link"===i.type&&(i.tokens=this.inlineTokens(i.text,[],!0,r)),t.push(i);else if(i=this.tokenizer.strong(e))e=e.substring(i.raw.length),i.tokens=this.inlineTokens(i.text,[],n,r),t.push(i);else if(i=this.tokenizer.em(e))e=e.substring(i.raw.length),i.tokens=this.inlineTokens(i.text,[],n,r),t.push(i);else if(i=this.tokenizer.codespan(e))e=e.substring(i.raw.length),t.push(i);else if(i=this.tokenizer.br(e))e=e.substring(i.raw.length),t.push(i);else if(i=this.tokenizer.del(e))e=e.substring(i.raw.length),i.tokens=this.inlineTokens(i.text,[],n,r),t.push(i);else if(i=this.tokenizer.autolink(e,V))e=e.substring(i.raw.length),t.push(i);else if(n||!(i=this.tokenizer.url(e,V))){if(i=this.tokenizer.inlineText(e,r,G))e=e.substring(i.raw.length),t.push(i);else if(e){var s="Infinite loop on byte: "+e.charCodeAt(0);if(this.options.silent){console.error(s);break}throw new Error(s)}}else e=e.substring(i.raw.length),t.push(i);return t},e=n,r=[{key:"rules",get:function(){return{block:W,inline:X}}}],(t=null)&&s(e.prototype,t),r&&s(e,r),n}(),J=t.defaults,K=$,Q=y,Y=function(){function e(e){this.options=e||J}var t=e.prototype;return t.code=function(e,t,n){var r,i=(t||"").match(/\S*/)[0];return!this.options.highlight||null!=(r=this.options.highlight(e,i))&&r!==e&&(n=!0,e=r),i?'<pre><code class="'+this.options.langPrefix+Q(i,!0)+'">'+(n?e:Q(e,!0))+"</code></pre>\n":"<pre><code>"+(n?e:Q(e,!0))+"</code></pre>\n"},t.blockquote=function(e){return"<blockquote>\n"+e+"</blockquote>\n"},t.html=function(e){return e},t.heading=function(e,t,n,r){return this.options.headerIds?"<h"+t+' id="'+this.options.headerPrefix+r.slug(n)+'">'+e+"</h"+t+">\n":"<h"+t+">"+e+"</h"+t+">\n"},t.hr=function(){return this.options.xhtml?"<hr/>\n":"<hr>\n"},t.list=function(e,t,n){var r=t?"ol":"ul";return"<"+r+(t&&1!==n?' start="'+n+'"':"")+">\n"+e+"</"+r+">\n"},t.listitem=function(e){return"<li>"+e+"</li>\n"},t.checkbox=function(e){return"<input "+(e?'checked="" ':"")+'disabled="" type="checkbox"'+(this.options.xhtml?" /":"")+"> "},t.paragraph=function(e){return"<p>"+e+"</p>\n"},t.table=function(e,t){return"<table>\n<thead>\n"+e+"</thead>\n"+(t=t&&"<tbody>"+t+"</tbody>")+"</table>\n"},t.tablerow=function(e){return"<tr>\n"+e+"</tr>\n"},t.tablecell=function(e,t){var n=t.header?"th":"td";return(t.align?"<"+n+' align="'+t.align+'">':"<"+n+">")+e+"</"+n+">\n"},t.strong=function(e){return"<strong>"+e+"</strong>"},t.em=function(e){return"<em>"+e+"</em>"},t.codespan=function(e){return"<code>"+e+"</code>"},t.br=function(){return this.options.xhtml?"<br/>":"<br>"},t.del=function(e){return"<del>"+e+"</del>"},t.link=function(e,t,n){if(null===(e=K(this.options.sanitize,this.options.baseUrl,e)))return n;var r='<a href="'+Q(e)+'"';return t&&(r+=' title="'+t+'"'),r+=">"+n+"</a>"},t.image=function(e,t,n){if(null===(e=K(this.options.sanitize,this.options.baseUrl,e)))return n;var r='<img src="'+e+'" alt="'+n+'"';return t&&(r+=' title="'+t+'"'),r+=this.options.xhtml?"/>":">"},t.text=function(e){return e},e}(),ee=function(){function e(){}var t=e.prototype;return t.strong=function(e){return e},t.em=function(e){return e},t.codespan=function(e){return e},t.del=function(e){return e},t.html=function(e){return e},t.text=function(e){return e},t.link=function(e,t,n){return""+n},t.image=function(e,t,n){return""+n},t.br=function(){return""},e}(),te=function(){function e(){this.seen={}}return e.prototype.slug=function(e){var t=e.toLowerCase().trim().replace(/<[!\/a-z].*?>/gi,"").replace(/[\u2000-\u206F\u2E00-\u2E7F\\'!"#$%&()*+,./:;<=>?@[\]^`{|}~]/g,"").replace(/\s/g,"-");if(this.seen.hasOwnProperty(t))for(var n=t;this.seen[n]++,t=n+"-"+this.seen[n],this.seen.hasOwnProperty(t););return this.seen[t]=0,t},e}(),ne=t.defaults,re=_,ie=function(){function n(e){this.options=e||ne,this.options.renderer=this.options.renderer||new Y,this.renderer=this.options.renderer,this.renderer.options=this.options,this.textRenderer=new ee,this.slugger=new te}n.parse=function(e,t){return new n(t).parse(e)};var e=n.prototype;return e.parse=function(e,t){void 0===t&&(t=!0);for(var n,r,i,s,a,l,o,c,h,u,p,g,f,d,k,b,m,x="",w=e.length,v=0;v<w;v++)switch((h=e[v]).type){case"space":continue;case"hr":x+=this.renderer.hr();continue;case"heading":x+=this.renderer.heading(this.parseInline(h.tokens),h.depth,re(this.parseInline(h.tokens,this.textRenderer)),this.slugger);continue;case"code":x+=this.renderer.code(h.text,h.lang,h.escaped);continue;case"table":for(l=o="",i=h.header.length,n=0;n<i;n++)l+=this.renderer.tablecell(this.parseInline(h.tokens.header[n]),{header:!0,align:h.align[n]});for(o+=this.renderer.tablerow(l),c="",i=h.cells.length,n=0;n<i;n++){for(l="",s=(a=h.tokens.cells[n]).length,r=0;r<s;r++)l+=this.renderer.tablecell(this.parseInline(a[r]),{header:!1,align:h.align[r]});c+=this.renderer.tablerow(l)}x+=this.renderer.table(o,c);continue;case"blockquote":c=this.parse(h.tokens),x+=this.renderer.blockquote(c);continue;case"list":for(u=h.ordered,p=h.start,g=h.loose,i=h.items.length,c="",n=0;n<i;n++)k=(d=h.items[n]).checked,b=d.task,f="",d.task&&(m=this.renderer.checkbox(k),g?0<d.tokens.length&&"text"===d.tokens[0].type?(d.tokens[0].text=m+" "+d.tokens[0].text,d.tokens[0].tokens&&0<d.tokens[0].tokens.length&&"text"===d.tokens[0].tokens[0].type&&(d.tokens[0].tokens[0].text=m+" "+d.tokens[0].tokens[0].text)):d.tokens.unshift({type:"text",text:m}):f+=m),f+=this.parse(d.tokens,g),c+=this.renderer.listitem(f,b,k);x+=this.renderer.list(c,u,p);continue;case"html":x+=this.renderer.html(h.text);continue;case"paragraph":x+=this.renderer.paragraph(this.parseInline(h.tokens));continue;case"text":for(c=h.tokens?this.parseInline(h.tokens):h.text;v+1<w&&"text"===e[v+1].type;)c+="\n"+((h=e[++v]).tokens?this.parseInline(h.tokens):h.text);x+=t?this.renderer.paragraph(c):c;continue;default:var y='Token with "'+h.type+'" type was not found.';if(this.options.silent)return void console.error(y);throw new Error(y)}return x},e.parseInline=function(e,t){t=t||this.renderer;for(var n,r="",i=e.length,s=0;s<i;s++)switch((n=e[s]).type){case"escape":r+=t.text(n.text);break;case"html":r+=t.html(n.text);break;case"link":r+=t.link(n.href,n.title,this.parseInline(n.tokens,t));break;case"image":r+=t.image(n.href,n.title,n.text);break;case"strong":r+=t.strong(this.parseInline(n.tokens,t));break;case"em":r+=t.em(this.parseInline(n.tokens,t));break;case"codespan":r+=t.codespan(n.text);break;case"br":r+=t.br();break;case"del":r+=t.del(this.parseInline(n.tokens,t));break;case"text":r+=t.text(n.text);break;default:var a='Token with "'+n.type+'" type was not found.';if(this.options.silent)return void console.error(a);throw new Error(a)}return r},n}(),se=A,ae=Z,le=y,oe=t.getDefaults,ce=t.changeDefaults,he=t.defaults;function ue(e,n,r){if(null==e)throw new Error("marked(): input parameter is undefined or null");if("string"!=typeof e)throw new Error("marked(): input parameter is of type "+Object.prototype.toString.call(e)+", string expected");if("function"==typeof n&&(r=n,n=null),n=se({},ue.defaults,n||{}),ae(n),r){var i,s=n.highlight;try{i=H.lex(e,n)}catch(e){return r(e)}var a=function(t){var e;if(!t)try{e=ie.parse(i,n)}catch(e){t=e}return n.highlight=s,t?r(t):r(null,e)};if(!s||s.length<3)return a();if(delete n.highlight,!i.length)return a();var l=0;return ue.walkTokens(i,function(n){"code"===n.type&&(l++,setTimeout(function(){s(n.text,n.lang,function(e,t){return e?a(e):(null!=t&&t!==n.text&&(n.text=t,n.escaped=!0),void(0===--l&&a()))})},0))}),void(0===l&&a())}try{var t=H.lex(e,n);return n.walkTokens&&ue.walkTokens(t,n.walkTokens),ie.parse(t,n)}catch(e){if(e.message+="\nPlease report this to https://github.com/markedjs/marked.",n.silent)return"<p>An error occurred:</p><pre>"+le(e.message+"",!0)+"</pre>";throw e}}return ue.options=ue.setOptions=function(e){return se(ue.defaults,e),ce(ue.defaults),ue},ue.getDefaults=oe,ue.defaults=he,ue.use=function(l){var t,n=se({},l);l.renderer&&function(){var a=ue.defaults.renderer||new Y;for(var e in l.renderer)!function(i){var s=a[i];a[i]=function(){for(var e=arguments.length,t=new Array(e),n=0;n<e;n++)t[n]=arguments[n];var r=l.renderer[i].apply(a,t);return!1===r&&(r=s.apply(a,t)),r}}(e);n.renderer=a}(),l.tokenizer&&function(){var a=ue.defaults.tokenizer||new D;for(var e in l.tokenizer)!function(i){var s=a[i];a[i]=function(){for(var e=arguments.length,t=new Array(e),n=0;n<e;n++)t[n]=arguments[n];var r=l.tokenizer[i].apply(a,t);return!1===r&&(r=s.apply(a,t)),r}}(e);n.tokenizer=a}(),l.walkTokens&&(t=ue.defaults.walkTokens,n.walkTokens=function(e){l.walkTokens(e),t&&t(e)}),ue.setOptions(n)},ue.walkTokens=function(e,t){for(var n,r=g(e);!(n=r()).done;){var i=n.value;switch(t(i),i.type){case"table":for(var s,a=g(i.tokens.header);!(s=a()).done;){var l=s.value;ue.walkTokens(l,t)}for(var o,c=g(i.tokens.cells);!(o=c()).done;)for(var h,u=g(o.value);!(h=u()).done;){var p=h.value;ue.walkTokens(p,t)}break;case"list":ue.walkTokens(i.items,t);break;default:i.tokens&&ue.walkTokens(i.tokens,t)}}},ue.Parser=ie,ue.parser=ie.parse,ue.Renderer=Y,ue.TextRenderer=ee,ue.Lexer=H,ue.lexer=H.lex,ue.Tokenizer=D,ue.Slugger=te,ue.parse=ue});