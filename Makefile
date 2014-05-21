# Programs #####################################################################

# Usage: $(call SVG2PNG,in-file,out-file[,width,height,background-color])
# SVG2PNG: Each is buggy in their own way
# ImageMagick: doesn't support transparency
#SVG2PNG = convert $1 -format png $(if $3,-resize $3x)$(if $4,$4) $(if $5,-background $5) $2
# librsvg: doesn't support <style> element
#SVG2PNG = rsvg-convert $1 -o $2 $(if $3,-w $3) $(if $4,-h $4) $(if $5,--background-color=$5)
# Inkscape: messes up gradients
SVG2PNG = inkscape $1 --export-png $2 $(if $3,-w $3) $(if $4,-h $4) $(if $5,-b '$5')

ANDROID = android
ANT = ant
MKDIRS = mkdir -p
RM = rm -f

################################################################################

icons = minak
densities = mdpi hdpi xhdpi xxhdpi xxxhdpi
resources = $(addsuffix $(addsuffix .png,$(icons)),$(addprefix res/drawable-,$(addsuffix /,$(densities))))

all: bin/minak-release-unsigned.apk

bin/minak-release-unsigned.apk: $(resources) build.xml
	$(ANT) release

build.xml:
	$(ANDROID) update project -p .

res/drawable-mdpi/%.png: res/drawable/%.svg
	$(MKDIRS) $(@D)
	$(call SVG2PNG,$<,$@,48)
res/drawable-hdpi/%.png: res/drawable/%.svg
	$(MKDIRS) $(@D)
	$(call SVG2PNG,$<,$@,72)
res/drawable-xhdpi/%.png: res/drawable/%.svg
	$(MKDIRS) $(@D)
	$(call SVG2PNG,$<,$@,96)
res/drawable-xxhdpi/%.png: res/drawable/%.svg
	$(MKDIRS) $(@D)
	$(call SVG2PNG,$<,$@,144)
res/drawable-xxxhdpi/%.png: res/drawable/%.svg
	$(MKDIRS) $(@D)
	$(call SVG2PNG,$<,$@,192)

clean: PHONY
	$(RM) -r bin gen build.xml
distclean: clean PHONY
	$(RM) local.properties
maintainerclean: distclean PHONY
	$(RM) -- $(resources)

.DELETE_ON_ERROR:
.PHONY: PHONY
