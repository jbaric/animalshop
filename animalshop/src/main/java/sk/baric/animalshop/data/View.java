package sk.baric.animalshop.data;

/**
 * Different views for json view filters
 * 
 * @author Juraj Baric
 *
 */
public class View {

	public static class ProductView {
		public static class Overview {}
		public static class Detail extends Overview {}
	}

	public static class OrderView {

		public static class OwnersOrder {}
		public static class AdminOrder extends OwnersOrder {}
	}
}
