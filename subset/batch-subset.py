import os
import glob
import subprocess

def main():
    # Buat folder khusus untuk menyimpan hasil subsetting
    output_dir = "subsetted_fonts"
    os.makedirs(output_dir, exist_ok=True)

    # Cari semua file font (.ttf dan .otf) di folder tempat skrip ini berada
    font_files = glob.glob("*.ttf") + glob.glob("*.otf")

    if not font_files:
        print("❌ Tidak ada file .ttf atau .otf yang ditemukan di folder ini.")
        return

    # Unicode ranges untuk Latin, Ibrani (Biblical), dan Yunani (Koine)
    unicodes = (
        "U+0020-007F," # Basic Latin
        "U+00A0-00FF," # Latin-1 Supplement
        "U+2000-206F," # General Punctuation
        "U+0590-05C7," # Hebrew (Konsonan, Niqqud, Cantillation)
        "U+FB1D-FB4F," # Hebrew Presentation Forms-A
        "U+0370-03FF," # Greek and Coptic
        "U+1F00-1FFF"  # Greek Extended (Polytonic / Teks Alkitab)
    )

    print(f"🔍 Ditemukan {len(font_files)} file font. Memulai proses batch subsetting...\n")

    for font_file in font_files:
        # Nama file output tetap sama, tapi disimpan di folder subsetted_fonts
        output_font = os.path.join(output_dir, font_file)
        
        cmd = [
            "pyftsubset",
            font_file,
            f"--unicodes={unicodes}",
            "--layout-features=*",
            "--glyph-names",
            "--symbol-cmap",
            f"--output-file={output_font}"
        ]

        print(f"⚙️ Memproses: {font_file}...")
        try:
            subprocess.run(cmd, check=True)
            print(f"   ✅ Berhasil disimpan ke -> {output_font}")
        except subprocess.CalledProcessError:
            print(f"   ❌ Gagal memproses {font_file}. Lewati file ini.")
        except FileNotFoundError:
            print("\n❌ Error: Perintah 'pyftsubset' tidak ditemukan.")
            print("Pastikan fonttools sudah terinstall dengan menjalankan: pip install fonttools")
            return

    print(f"\n🚀 Selesai! Semua font hasil optimasi telah tersimpan di dalam folder '{output_dir}'.")
    print("Silakan salin font dari folder tersebut ke project LibCat Anda.")

if __name__ == "__main__":
    main()