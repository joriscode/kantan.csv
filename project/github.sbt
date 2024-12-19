credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  "_", // user is ignored
  sys.env.get("GITHUB_TOKEN") match {
    case None =>
      sLog.value.info("No GITHUB_TOKEN environment variable found, publishing to GitHub will fail")
      ""
    case Some(value) =>
      sLog.value.info("GITHUB_TOKEN environment variable provided, publishing to GitHub is enabled")
      value
  }
)

resolvers += "Github packages" at "https://maven.pkg.github.com/joriscode/kantan.sbt"
