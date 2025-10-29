package PKG.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class SiteMeshFitler extends ConfigurableSiteMeshFilter {

	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder.addDecoratorPath("/*", "/default.jsp")
				.addDecoratorPath("/homepage*", "/default.jsp")
				.addDecoratorPath("/user/*", "/user_layout.jsp").addDecoratorPath("/admin/*", "/admin_layout.jsp")
				.addDecoratorPath("/item/getinfo/*", "/item_detail_layout.jsp")
				.addExcludedPath("/redirect/*").addExcludedPath("/user/login").addExcludedPath("/user/register")
				.addExcludedPath("/user/chatroom").addExcludedPath("/user/changepassw");
	}
}
