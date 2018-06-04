package core;

public class MutationUtils {
	
	public Genome crossoverGenome(Genome genome1, Genome genom2) {
		if(genom2.fitness > genome1.fitness) {
			Genome temp = genome1;
			genome1 = genom2;
			genom2 = temp;
		}
		
		Genome child = new Genome();
		
		
	}
   
    local innovations2 = {}
    for i=1,#g2.genes do
            local gene = g2.genes[i]
            innovations2[gene.innovation] = gene
    end
   
    for i=1,#g1.genes do
            local gene1 = g1.genes[i]
            local gene2 = innovations2[gene1.innovation]
            if gene2 ~= nil and math.random(2) == 1 and gene2.enabled then
                    table.insert(child.genes, copyGene(gene2))
            else
                    table.insert(child.genes, copyGene(gene1))
            end
    end
   
    child.maxneuron = math.max(g1.maxneuron,g2.maxneuron)
   
    for mutation,rate in pairs(g1.mutationRates) do
            child.mutationRates[mutation] = rate
    end
   
    return child
end
}
