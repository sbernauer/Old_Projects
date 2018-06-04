package core;

public class Gene {
	int into = 0;
	int out = 0;
	int weight = 0;
	boolean enabled = true;
	private int innovation = 0;
	
	public Gene() {
	}
	
	public static Gene copyGene(Gene gene) {
		Gene copiedGene = new Gene();
		copiedGene.into = gene.into;
		copiedGene.out = gene.out;
		copiedGene.weight = gene.weight;
		copiedGene.enabled = gene.enabled;
		copiedGene.innovation = gene.innovation;
		return copiedGene;
	}
	
//	function newGene()
//    local gene = {}
//    gene.into = 0
//    gene.out = 0
//    gene.weight = 0.0
//    gene.enabled = true
//    gene.innovation = 0
//   
//    return gene
//end
	
//	function copyGene(gene)
//    local gene2 = newGene()
//    gene2.into = gene.into
//    gene2.out = gene.out
//    gene2.weight = gene.weight
//    gene2.enabled = gene.enabled
//    gene2.innovation = gene.innovation
//   
//    return gene2
//end
}
