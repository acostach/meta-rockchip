# rk3277 Board u-boot

require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "u-boot which includes support for the rk3288 Board."
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI[md5sum] = "d9ec739eb8dff1d550c27f716bfd609d"
SRC_URI[sha256sum] = "0cb1fcb21f50a4d9464cca7eeebe7b75a88d1d094896c8341461c69ba20add17"

TAG = "release-20171218"
SRC_URI = " \
	https://github.com/rockchip-linux/u-boot.git;tag=${TAG};nobranch=1; \
	file://binutils-2.28-ld-fix.patch \
"
S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(firefly-rk3288|aio-3288c)"
DEPENDS_append = " bc-native dtc-native"
inherit pythonnative

IDBLOADER = "idbloader.img"

do_compile_append () {
    cp ${B}/spl/${SPL_BINARY} ${B}/${SPL_BINARY}
}

do_deploy_append () {
    # Create bootloader image
    ${B}/tools/mkimage -n ${SOC_FAMILY} -T rksd -d ${B}/spl/${SPL_BINARY} ${DEPLOYDIR}/${IDBLOADER}
    cat ${B}/u-boot.bin >>${DEPLOYDIR}/${IDBLOADER}
}
