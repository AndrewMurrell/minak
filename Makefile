# SVG2PNG: Each is buggy in their own way
# ImageMagick: doesn't support transparency
#SVG2PNG = convert $1 -format png $(if $3,-resize $3x)$(if $4,$4) $(if $5,-bacground $5) $2
# librsvg: doesn't support <style> element
#SVG2PNG = rsvg-convert $1 -o $2 $(if $3,-w $3) $(if $4,-h $4) $(if $5,--background-color=$5)
# Inkscape: messes up gradients
_SVG2PNG = inkscape $1 --export-png $2 $(if $3,-w $3) $(if $4,-h $4) $(if $5,-b '$5')

# Hacky thing to also run it through pngcrush
SVG2PNG = { $(call _SVG2PNG,$1,$2.tmp.png,$3) && pngcrush $2.tmp.png $2 && rm $2.tmp.png; } || { rm $2.tmp.png; false; }

icons = minak
densities = mdpi hdpi xhdpi xxhdpi xxxhdpi
all: $(addsuffix $(addsuffix .png,$(icons)),$(addprefix res/drawable-,$(addsuffix /,$(densities))))

res/drawable-mdpi/%.png: res/drawable/%.svg
	mkdir -p $(@D)
	$(call SVG2PNG,$<,$@,48)
res/drawable-hdpi/%.png: res/drawable/%.svg
	mkdir -p $(@D)
	$(call SVG2PNG,$<,$@,72)
res/drawable-xhdpi/%.png: res/drawable/%.svg
	mkdir -p $(@D)
	$(call SVG2PNG,$<,$@,96)
res/drawable-xxhdpi/%.png: res/drawable/%.svg
	mkdir -p $(@D)
	$(call SVG2PNG,$<,$@,144)
res/drawable-xxxhdpi/%.png: res/drawable/%.svg
	mkdir -p $(@D)
	$(call SVG2PNG,$<,$@,192)

.DELETE_ON_ERROR:
