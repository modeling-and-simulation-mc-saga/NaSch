set terminal pdfcairo enhanced color size 29cm,20cm font "Arial" fontscale 1
set xlabel "{/Symbol r}"
set ylabel "{/:Italic q}"
set yrange [0:1]
set output "fundamental.pdf"
p=0.2
vmax=5
xc = 1./(vmax+1)
set title "Fundamental Diagram ({/:Italic L}=100, {/:Italic p}=0.2 {/Italic v}_{max}=5)"
set arrow 1 from xc,0 to xc,1 lw 3 nohead
set label 1 "1/({/:Italic v}_{max}+1)" at 0.2,0.05
set label 2 "{/:Italic q} = (1 - {/Symbol r})" at 0.5,0.6
set label 3 "{/:Italic q} = ({/:Italic v}_{max} - {/:Italic p}) {/Symbol r}" at 0.2,0.9
plot "fundamental.txt" pt 7 lc 7 notitle,(vmax-p)*x lw 3 lc 6 notitle,(1-x) lw 3 lc 9 notitle
#
p=0.
set output "fundamental-deterministic.pdf"
set title "Fundamental Diagram ({/:Italic L}=100, {/Italic v}_{max}=5, deterministic)"
set arrow 1 from xc,0 to xc,1 lw 3 nohead
set label 1 "1/({/:Italic v}_{max}+1)" at 0.2,0.05
set label 2 "{/:Italic q} = 1 - {/Symbol r}" at 0.5,0.6
set label 3 "{/:Italic q} = {/:Italic v}_{max}{/Symbol r}" at 0.2,0.9
plot "fundamental-deterministic.txt" pt 7 lc 7 notitle,(vmax-p)*x lw 3 lc 6 notitle,(1-x) lw 3 lc 9 notitle