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

ThisBuild / resolvers += "Github packages sbt" at "https://maven.pkg.github.com/joriscode/kantan.sbt"
ThisBuild / resolvers += "Github packages codecs" at "https://maven.pkg.github.com/joriscode/kantan.codecs"
