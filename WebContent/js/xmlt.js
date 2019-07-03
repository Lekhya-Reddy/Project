(function($, _, scope) {
	var templates = {};

	_.templateSettings.interpolate = /{{([\s\S]+?)}}/g;
	_.templateSettings.evaluate = /{%([\s\S]+?)%}/g;
	_.templateSettings.escape = /{{-([\s\S]+?)}}/g;

	_.templateSettings.imports.include = function(name, context) {
		return templates[name](context);
	};

	_.templateSettings.imports.applyTemplate = function(node, name, extras) {
		function applyToNode(node) {
			var nodeName = name || node.$name;
			if(templates[nodeName]) {
				return templates[nodeName](_.assign(node, extras || {}));
			}
			return '';
		};

		return node.map? node.map(applyToNode).join('\n'): applyToNode(node);
	};

	_.templateSettings.imports.makePairs = function(str) {
		return _.map(str.split('||'), function(pair) {
			var split = pair.split('=', 2);
			return { key: split[0], value: split[1] }
		});
	};

  var uniqueIdRegEx = /([\D]+)?([\d]+)/;
  
  _.templateSettings.imports.next = function(str) {
    var segments = uniqueIdRegEx.exec(str);
    return (segments[1]?segments[1]:'') + (parseInt(segments[2]) + 1);
  };

	function elementNodeFilter(node) { return node.nodeType === 1; }
	function $nodeWrap(child) { return $node(child); }

	var $node = 
	_.templateSettings.imports.node = function(node) {
		//console.info($.getJSON(node));
		
		return {
			$name: node.nodeName,
			$attrs: _(node.attributes).map(
				function(attr) { return [attr.name, attr.value]; }).object().value(),
			$text: _.constant(node.textContent),
			$nodes: function(name) {
				//console.info(name, node)
				return _(name? $('> ' + name, node) : node.childNodes)
					.filter(elementNodeFilter).map($nodeWrap);
			},
			$uid: _.uniqueId(node.nodeName)
		}
	};

	$(function() {
		$('script[type="text/xmlt"][id]').each(function(index, tmplNode) {
			var alias;
			templates[tmplNode.id] = _.template(tmplNode.text);
			if(alias = $(tmplNode).attr('alias')) {
				_.forEach(alias.split(','), function(name) {
					templates[name] = templates[tmplNode.id];
				});
			}
		});
	});

	scope.parse = function(name, node) {
		return templates[name]($node(node));
	};
})($, _, window);